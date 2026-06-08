package com.app.realestate.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LLMPrompt {
    @JsonProperty("user_prompt")
    private String userPrompt;

    @JsonProperty("system_prompt")
    private String systemPrompt;

    public LLMPrompt(String userPrompt, String systemPrompt) {
        this.userPrompt = userPrompt;
        this.systemPrompt = systemPrompt;
    }

    public String getUserPrompt() {
        return userPrompt;
    }

    public void setUserPrompt(String userPrompt) {
        this.userPrompt = userPrompt;
    }

    public String getSystemPrompt() {
        return systemPrompt;
    }

    public void setSystemPrompt(String systemPrompt) {
        this.systemPrompt = systemPrompt;
    }
}
