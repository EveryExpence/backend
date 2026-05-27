package com.every.expence.receipts.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ReceiptAnalysisResponse(
    @JsonProperty("store_name") String storeName,
    java.util.List<Product> products,
    Location location,
    Double amount,
    String category,
    @JsonProperty("payment_method") String paymentMethod
) {
    public record Product(
        String name,
        Double price
    ) {}

    public record Location(
        Double lat,
        Double lng,
        String city
    ) {}
}