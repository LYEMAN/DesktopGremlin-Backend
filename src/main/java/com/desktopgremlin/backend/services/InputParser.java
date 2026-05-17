package com.desktopgremlin.backend.services;

import org.springframework.stereotype.Component;

@Component
public class InputParser {

    public ParsedMessage parse(String raw) {
        if (raw == null || raw.isBlank()) {
            return new ParsedMessage("neutral", raw);
        }

        String lower = raw.toLowerCase();

        // Mood detection
        String mood;
        if (lower.contains("happy") || lower.contains("great") || lower.contains("love"))
            mood = "happy";
        else if (lower.contains("sad") || lower.contains("upset") || lower.contains("depressed"))
            mood = "sad";
        else if (lower.contains("angry") || lower.contains("mad") || lower.contains("hate"))
            mood = "angry";
        else
            mood = "neutral";

        // Topic detection
        /*   String topic;
            if (lower.contains("weather") || lower.contains("temperature") || lower.contains("rain"))
                topic = "weather";
            else if (lower.contains("joke") || lower.contains("funny") || lower.contains("laugh"))
                topic = "humor";
            else if (lower.contains("math") || lower.contains("calculate") || lower.contains("solve"))
                topic = "math";
            else
                topic = "general"; */

        return new ParsedMessage(mood, raw);
    }

    public record ParsedMessage(String mood, String originalMessage) {}
}
