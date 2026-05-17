package com.desktopgremlin.backend.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class AiService {

    @Value("${groq.api.key}")
    private String groqApiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    public String generateResponse(String userMessage) {

        String url = "https://api.groq.com/openai/v1/chat/completions";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(groqApiKey);

        Map<String, Object> body = new HashMap<>();
        body.put("model", "llama-3.1-8b-instant"); 

        Map<String, String> systemMsg = new HashMap<>();

        systemMsg.put("role", "system");
        systemMsg.put("content", PromptLoader.loadPrompt("/docs/json_formatting.txt"));

        List<Map<String, String>> messages = new ArrayList<>();
        Map<String, String> userMsg = new HashMap<>();
        userMsg.put("role", "user");
        userMsg.put("content", userMessage); 
        messages.add(systemMsg);
        messages.add(userMsg);

        body.put("messages", messages);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response.getBody());

            return root.get("choices").get(0).get("message").get("content").asText();
        } catch (Exception ex) {
            ex.printStackTrace();
            return "Error contacting AI service.";
        }
    }
}
