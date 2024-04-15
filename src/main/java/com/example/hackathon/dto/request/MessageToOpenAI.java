package com.example.hackathon.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MessageToOpenAI {
    @JsonProperty("role")
    private String role;

    @JsonProperty("content")
    private String content;

    public MessageToOpenAI(String role, String content) {
        this.role = role;
        this.content = content;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
