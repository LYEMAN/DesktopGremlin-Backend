package com.desktopgremlin.backend.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class FactService {

    private static final String FACT_URL = "https://uselessfacts.jsph.pl/api/v2/facts/random?language=en";
    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public String getRandomFact() {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(List.of(MediaType.APPLICATION_JSON));

            HttpEntity<Void> request = new HttpEntity<>(headers);
            ResponseEntity<String> response = restTemplate.exchange(FACT_URL, HttpMethod.GET, request, String.class);

            JsonNode root = objectMapper.readTree(response.getBody());
            return root.has("text") ? root.get("text").asText() : "";
        } catch (Exception ex) {
            ex.printStackTrace();
            return "Error fetching random fact.";
        }
    }
}

