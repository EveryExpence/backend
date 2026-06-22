package com.every.expence.exchangerate;

import java.util.Map;

public record ExchangeRateResponseDTO(
        String result,
        String base_code,
        Map<String, Double> conversion_rates
) {}
