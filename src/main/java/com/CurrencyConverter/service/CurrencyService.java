package com.CurrencyConverter.service;

import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CurrencyService {

    @Value("${currency.api.url}")
    private String apiUrl;

    public Set<String> getAllCurrencies() {
        RestTemplate restTemplate = new RestTemplate();
        String url = apiUrl + "USD"; // Fetch base exchange rates from USD
        
        Map<String, Object> response = restTemplate.getForObject(url, Map.class);
        if (response != null && response.containsKey("rates")) {
            Map<String, Double> rates = (Map<String, Double>) response.get("rates");
            return rates.keySet(); // Return all available currency codes
        }
        return Set.of();
    }

    public double convertCurrency(String from, String to, double amount) {
        RestTemplate restTemplate = new RestTemplate();
        String url = apiUrl + from;

        Map<String, Object> response = restTemplate.getForObject(url, Map.class);
        if (response != null && response.containsKey("rates")) {
            Map<String, Double> rates = (Map<String, Double>) response.get("rates");
            if (rates.containsKey(to)) {
                double rate = rates.get(to);
                return amount * rate;
            }
        }
        return 0;
    }
}
