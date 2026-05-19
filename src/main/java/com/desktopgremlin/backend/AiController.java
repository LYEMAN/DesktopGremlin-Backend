package com.desktopgremlin.backend;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.desktopgremlin.backend.models.ChatRequest;
import com.desktopgremlin.backend.models.ChatResponse;
import com.desktopgremlin.backend.services.AiService;
import com.desktopgremlin.backend.services.PromptLoader;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/ai")
@CrossOrigin(origins = "*")
public class AiController {

    private final AiService aiService;
    @Value("${groq.api.key}")
    private String groqApiKey;
    private final RestTemplate restTemplate = new RestTemplate();
 

    public AiController(AiService aiService) {
        this.aiService = aiService;
    }

    @PostMapping("/chat")
    public ChatResponse chat(@RequestBody ChatRequest request) { 
        
    System.out.println("AI endpoint hit");

    String message = request.getMessage();

    System.out.println("Message: " + message);

   
        String prompt = request.getMessage(); 

        String rawJson = null;
        try {
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
            userMsg.put("content", prompt);
            messages.add(systemMsg);
            messages.add(userMsg);

            body.put("messages", messages);

            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);
            ResponseEntity<String> resp = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
            rawJson = resp.getBody();
            System.out.println("AI raw response: " + rawJson);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        String reply;
        if (rawJson != null) {
            try {
                ObjectMapper mapper = new ObjectMapper();
                JsonNode root = mapper.readTree(rawJson);
                reply = root.get("choices").get(0).get("message").get("content").asText();
            } catch (Exception ex) {
                ex.printStackTrace();
                reply = aiService.generateResponse(prompt);
            }
        } else {
            reply = aiService.generateResponse(prompt);
        }

        System.out.println("Response: " + reply);
        return new ChatResponse(reply);
    }
}
