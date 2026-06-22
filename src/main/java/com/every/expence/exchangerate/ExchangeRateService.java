package com.every.expence.exchangerate;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ExchangeRateService {

    @Value("${exchange-rates.api-key}")
    private String apiKey;

    @Value("${exchange-rates.base-url}")
    private String baseUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    public ExchangeRateResponseDTO getExchangeRates(String baseCurrency) {
        String url = String.format("%s/%s/latest/%s", baseUrl, apiKey, baseCurrency);
        return restTemplate.getForObject(url, ExchangeRateResponseDTO.class);
    }
}
