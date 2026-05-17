package com.desktopgremlin.backend.models;

public class ChatResponse {

    private String reply;
    private String message;

    public ChatResponse() {
    }

    public ChatResponse(String reply) {
        this.reply = reply;
        this.message = reply;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
        this.message = reply;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
        this.reply = message;
    }
}
