import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.*;

public class TestAPI {
    public static void main(String[] args) throws Exception {
        Map<String, Object> response = new HashMap<>();
        Map<String, Object> innerRates = new HashMap<>();
        innerRates.put("USD", 1);
        innerRates.put("EUR", 0.9);
        response.put("result", "success");
        response.put("base_code", "USD");
        response.put("rates", innerRates);

        Map<String, Double> rates = null;
        if (response.containsKey("conversion_rates")) {
            rates = (Map<String, Double>) response.get("conversion_rates");
        } else if (response.containsKey("rates")) {
            rates = (Map<String, Double>) response.get("rates");
        }

        ExchangeRateResponseDTO dto = new ExchangeRateResponseDTO("success", "USD", rates);
        ObjectMapper mapper = new ObjectMapper();
        System.out.println(mapper.writeValueAsString(dto));
    }
}
record ExchangeRateResponseDTO(String result, String base_code, Map<String, Double> conversion_rates) {}
