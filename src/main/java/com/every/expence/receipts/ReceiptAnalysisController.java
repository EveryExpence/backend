package com.every.expence.receipts;

import com.every.expence.receipts.dto.ReceiptAnalysisResponse;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
        @RequestParam("image") MultipartFile image
    ) {
        ReceiptAnalysisResponse result = receiptAnalysisService.analyze(image);
        return ResponseEntity.ok(result);
    }
}