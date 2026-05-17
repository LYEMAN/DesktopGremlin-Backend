package com.desktopgremlin.backend.services;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class PromptLoader {

    public static String loadPrompt(String resourcePath) {
        try (InputStream in = PromptLoader.class.getResourceAsStream(resourcePath)) {
            if (in == null) {
                throw new IOException("Resource not found: " + resourcePath);
            }
            return new String(in.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException ex) {
            ex.printStackTrace();
            return "";
        }
    }
}
