package com.every.expence.receipts;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Base64;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.every.expence.receipts.dto.ReceiptAnalysisResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ReceiptAnalysisService {
    private static final String GEMINI_URL_TEMPLATE =
        "https://generativelanguage.googleapis.com/v1beta/models/%s:generateContent?key=%s";
    private static final Set<String> ALLOWED_MIME_TYPES = Set.of("image/jpeg", "image/png", "image/jpg");
    private static final long MAX_IMAGE_BYTES = 10L * 1024L * 1024L;
    private static final Duration GEMINI_TIMEOUT = Duration.ofSeconds(30);

    private final ObjectMapper objectMapper;
    private final HttpClient httpClient;
    private final String apiKey;
    private final String model;

    public ReceiptAnalysisService(
        ObjectMapper objectMapper,
        @Value("${gemini.api-key}") String apiKey,
        @Value("${gemini.model}") String model
    ) {
        this.objectMapper = objectMapper;
        this.apiKey = apiKey;
        this.model = model;
        this.httpClient = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(10))
            .build();
    }

    private record ImageData(String base64Data, String mimeType) {}

    public ReceiptAnalysisResponse analyze(List<MultipartFile> images, List<String> categories, List<String> paymentMethods) {
        if (images == null || images.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Images are required");
        }

        List<ImageData> imageDataList = new java.util.ArrayList<>();
        for (MultipartFile image : images) {
            if (image == null || image.isEmpty()) {
                continue;
            }

            if (image.getSize() > MAX_IMAGE_BYTES) {
                throw new ResponseStatusException(HttpStatus.CONTENT_TOO_LARGE, "Image exceeds 10MB limit");
            }

            String mimeType = image.getContentType();
            if (mimeType == null || mimeType.isBlank()) {
                mimeType = "image/jpeg";
            }

            if (!ALLOWED_MIME_TYPES.contains(mimeType)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unsupported image type");
            }

            byte[] imageBytes;
            try {
                imageBytes = image.getBytes();
            } catch (IOException ex) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid image", ex);
            }

            String base64Image = Base64.getEncoder().encodeToString(imageBytes);
            imageDataList.add(new ImageData(base64Image, mimeType));
        }

        if (imageDataList.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Images are required");
        }

        String categoryConstraint;
        if (categories != null && !categories.isEmpty()) {
            String joined = categories.stream()
                .map(c -> "\"" + c + "\"")
                .collect(Collectors.joining(", "));
            categoryConstraint = "category must be exactly one of these values (case-sensitive): [" + joined + "], or null if none match.";
        } else {
            categoryConstraint = "category: a general expense category string, or null if unclear.";
        }

        String paymentMethodConstraint;
        if (paymentMethods != null && !paymentMethods.isEmpty()) {
            String joined = paymentMethods.stream()
                .map(p -> "\"" + p + "\"")
                .collect(Collectors.joining(", "));
            paymentMethodConstraint = "payment_method must be exactly one of these values (case-sensitive): [" + joined + "], or null if none match.";
        } else {
            paymentMethodConstraint = "payment_method must be strictly one of: cash, card, or null.";
        }

        String prompt = "Extract expense data from these receipt images into a single JSON object "
            + "with keys: store_name, products, location{lat,lng,city}, amount, category, payment_method. "
            + "No extra keys. "
            + "store_name: the store/merchant name, or null if not found. "
            + "products: list of line items; each item has {name, price}. If price is unclear, use null. "
            + "If no products can be detected, return an empty list. "
            + categoryConstraint + " "
            + paymentMethodConstraint + " "
            + "For location.city: use the city where the purchase happened (store location). "
            + "If multiple cities appear, prefer the one closest to PARAGON, SPRZEDAZ, or the date/time; otherwise null.";

        String requestBody = buildRequestBody(prompt, imageDataList);
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(String.format(GEMINI_URL_TEMPLATE, model, apiKey)))
            .header("Content-Type", "application/json")
            .timeout(GEMINI_TIMEOUT)
            .POST(HttpRequest.BodyPublishers.ofString(requestBody))
            .build();

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() < 200 || response.statusCode() >= 300) {
                throw new ResponseStatusException(
                    HttpStatus.BAD_GATEWAY,
                    "Gemini error: HTTP " + response.statusCode()
                );
            }

            JsonNode responseJson = objectMapper.readTree(response.body());
            JsonNode textNode = responseJson.path("candidates")
                .path(0)
                .path("content")
                .path("parts")
                .path(0)
                .path("text");

            if (textNode.isMissingNode() || textNode.isNull()) {
                throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "Gemini returned no content");
            }

            JsonNode payloadNode;
            if (textNode.isObject() || textNode.isArray()) {
                payloadNode = textNode;
            } else {
                payloadNode = objectMapper.readTree(textNode.asText());
            }

            ReceiptAnalysisResponse result;
            if (payloadNode.isArray()) {
                List<ReceiptAnalysisResponse> responses = new java.util.ArrayList<>();
                for (JsonNode node : payloadNode) {
                    responses.add(objectMapper.treeToValue(node, ReceiptAnalysisResponse.class));
                }
                result = mergeResponses(responses);
            } else {
                result = objectMapper.treeToValue(payloadNode, ReceiptAnalysisResponse.class);
            }
            return roundAmount(result);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
            Thread.currentThread().interrupt();
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "Gemini request interrupted", ex);
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "Gemini request failed", ex);
        }
    }

    private ReceiptAnalysisResponse mergeResponses(List<ReceiptAnalysisResponse> responses) {
        if (responses == null || responses.isEmpty()) {
            return new ReceiptAnalysisResponse(null, List.of(), null, null, null, null);
        }
        if (responses.size() == 1) {
            return responses.get(0);
        }

        String storeName = null;
        List<ReceiptAnalysisResponse.Product> products = new java.util.ArrayList<>();
        ReceiptAnalysisResponse.Location location = null;
        Double amount = null;
        String category = null;
        String paymentMethod = null;

        for (ReceiptAnalysisResponse res : responses) {
            if (res == null) {
                continue;
            }
            if (storeName == null && res.storeName() != null && !res.storeName().isBlank()) {
                storeName = res.storeName();
            }
            if (res.products() != null) {
                products.addAll(res.products());
            }
            if (location == null && res.location() != null) {
                location = res.location();
            }
            if (res.amount() != null) {
                if (amount == null) {
                    amount = 0.0;
                }
                amount += res.amount();
            }
            if (category == null && res.category() != null && !res.category().isBlank()) {
                category = res.category();
            }
            if (paymentMethod == null && res.paymentMethod() != null && !res.paymentMethod().isBlank()) {
                paymentMethod = res.paymentMethod();
            }
        }

        return new ReceiptAnalysisResponse(storeName, products, location, amount, category, paymentMethod);
    }

    private ReceiptAnalysisResponse roundAmount(ReceiptAnalysisResponse response) {
        if (response == null || response.amount() == null) {
            return response;
        }
        Double rounded = Math.round(response.amount() * 100.0) / 100.0;
        return new ReceiptAnalysisResponse(
            response.storeName(),
            response.products(),
            response.location(),
            rounded,
            response.category(),
            response.paymentMethod()
        );
    }

    private String buildRequestBody(String prompt, List<ImageData> images) {
        ObjectNode root = objectMapper.createObjectNode();
        ArrayNode contents = root.putArray("contents");
        ObjectNode content = contents.addObject();
        ArrayNode parts = content.putArray("parts");

        parts.addObject().put("text", prompt);
        for (ImageData img : images) {
            ObjectNode inlineData = parts.addObject().putObject("inline_data");
            inlineData.put("mime_type", img.mimeType());
            inlineData.put("data", img.base64Data());
        }

        ObjectNode generationConfig = root.putObject("generationConfig");
        generationConfig.put("responseMimeType", "application/json");

        return root.toString();
    }
}
