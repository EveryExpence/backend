package com.every.expence.exchangerate;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Map;

@Service
public class ExchangeRateService {

    @Value("${exchange-rates.api-key:}")
    private String apiKey;

    @Value("${exchange-rates.base-url:https://v6.exchangerate-api.com/v6}")
    private String baseUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    public ExchangeRateResponseDTO getExchangeRates(String baseCurrency) {
        String url;
        if (apiKey == null || apiKey.trim().isEmpty()) {
            url = String.format("https://open.er-api.com/v6/latest/%s", baseCurrency);
        } else {
            url = String.format("%s/%s/latest/%s", baseUrl, apiKey, baseCurrency);
        }
        
        Map<String, Object> response = restTemplate.getForObject(url, Map.class);
        if (response != null) {
            String result = (String) response.get("result");
            String baseCode = (String) response.get("base_code");
            Map<String, Double> rates = null;
            
            if (response.containsKey("conversion_rates")) {
                Object ratesObj = response.get("conversion_rates");
                if (ratesObj instanceof Map<?,?> map) {
                    rates = new java.util.HashMap<>();
                    for (Map.Entry<?,?> entry : map.entrySet()) {
                        if (entry.getKey() instanceof String key && entry.getValue() instanceof Number num) {
                            rates.put(key, num.doubleValue());
                        }
                    }
                }
            } else if (response.containsKey("rates")) {
                Object ratesObj = response.get("rates");
                if (ratesObj instanceof Map<?,?> map) {
                    rates = new java.util.HashMap<>();
                    for (Map.Entry<?,?> entry : map.entrySet()) {
                        if (entry.getKey() instanceof String key && entry.getValue() instanceof Number num) {
                            rates.put(key, num.doubleValue());
                        }
                    }
                }
            }
            
            return new ExchangeRateResponseDTO(result, baseCode, rates);
        }
        return null;
    }
}
