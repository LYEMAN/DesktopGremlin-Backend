package com.desktopgremlin.backend;

import com.desktopgremlin.backend.models.ChatRequest;
import com.desktopgremlin.backend.models.ChatResponse;
import com.desktopgremlin.backend.services.AiService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ai")
@CrossOrigin(origins = "*")
public class AiController {

    @Autowired
    private AiService aiService;

    @PostMapping("/chat")
    public ChatResponse chat(@RequestBody ChatRequest request) {

        String aiReply =
                aiService.generateResponse(request.getMessage());

        return new ChatResponse(aiReply);
    }
}