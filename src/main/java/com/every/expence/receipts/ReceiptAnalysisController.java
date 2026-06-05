package com.every.expence.receipts;

import com.every.expence.receipts.dto.ReceiptAnalysisResponse;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/receipts")
public class ReceiptAnalysisController {

    private final ReceiptAnalysisService receiptAnalysisService;

    public ReceiptAnalysisController(ReceiptAnalysisService receiptAnalysisService) {
        this.receiptAnalysisService = receiptAnalysisService;
    }

    @PostMapping(
        path = "/analyze",
        consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<ReceiptAnalysisResponse> analyze(
        @RequestParam("images") List<MultipartFile> images,
        @RequestParam(value = "categories", required = false) List<String> categories,
        @RequestParam(value = "paymentMethods", required = false) List<String> paymentMethods
    ) {
        ReceiptAnalysisResponse result = receiptAnalysisService.analyze(images, categories, paymentMethods);
        return ResponseEntity.ok(result);
    }
}
