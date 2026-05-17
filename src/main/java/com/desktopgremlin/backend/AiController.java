package com.desktopgremlin.backend;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.desktopgremlin.backend.models.ChatRequest;
import com.desktopgremlin.backend.models.ChatResponse;
import com.desktopgremlin.backend.services.AiService;
import com.desktopgremlin.backend.services.InputParser;

@RestController
@RequestMapping("/ai")
@CrossOrigin(origins = "*")
public class AiController {

    private final AiService aiService;
    private final InputParser inputParser;

    public AiController(AiService aiService, InputParser inputParser) {
        this.aiService = aiService;
        this.inputParser = inputParser;
    }

    @PostMapping("/chat")
    public ChatResponse chat(@RequestBody ChatRequest request) { 
        
    System.out.println("AI endpoint hit");

    String message = request.getMessage();

    System.out.println("Message: " + message);

   
        String prompt = request.getMessage(); 
       /*  // Enrich the prompt with detected context
        String enrichedPrompt = String.format(
            "[User mood: %s] %s",
            parsed.mood(),
            parsed.originalMessage()
        ); */

        String reply = aiService.generateResponse(prompt);
        return new ChatResponse(reply);
    }
}
