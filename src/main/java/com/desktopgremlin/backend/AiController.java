package com.desktopgremlin.backend;

import com.desktopgremlin.backend.models.ChatRequest;
import com.desktopgremlin.backend.models.ChatResponse;
import com.desktopgremlin.backend.services.AiService;
import com.desktopgremlin.backend.services.InputParser;
import org.springframework.web.bind.annotation.*;

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
        var parsed = inputParser.parse(request.getMessage());

        // Enrich the prompt with detected context
        String enrichedPrompt = String.format(
            "[User mood: %s] [Topic: %s] %s",
            parsed.mood(),
            parsed.topic(),
            parsed.originalMessage()
        );

        String reply = aiService.generateResponse(enrichedPrompt);
        return new ChatResponse(reply);
    }
}
