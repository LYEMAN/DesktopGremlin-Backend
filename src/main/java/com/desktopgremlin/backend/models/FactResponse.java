package com.desktopgremlin.backend.models;

public class FactResponse {

    private String fact;

    public FactResponse() {
    }

    public FactResponse(String fact) {
        this.fact = fact;
    }

    public String getFact() {
        return fact;
    }

    public void setFact(String fact) {
        this.fact = fact;
    }
}
