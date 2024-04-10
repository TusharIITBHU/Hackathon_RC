package com.example.hackathon.service;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;

@Service
public class SuggestFeatureServiceImpl implements SuggestFeatureService{

    private final String openaiApiUrl = "https://api.openai.com/v1/chat/completions";
    private final String openaiApiKey = "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";
    private final String openaiModel = "gpt-3.5-turbo";

    private final RestTemplate restTemplate;

    @Autowired
    public SuggestFeatureServiceImpl (RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public String getSuggestedFeature(String transcriptionSnippet) {
        String features = "1. Whiteboard: Helps in creating/designing architecture diagrams.\n" +
            "2. Polls: Helps moderator to create a poll with predefined options, to come to a decision fast.";

        OpenAiRequest request = new OpenAiRequest(openaiModel, features, transcriptionSnippet);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(openaiApiKey);

        HttpEntity<OpenAiRequest> entity = new HttpEntity<>(request, headers);

        ResponseEntity<String> response = restTemplate.exchange(openaiApiUrl, HttpMethod.POST, entity, String.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        } else {
            // Handle error response
            return "Error: " + response.getStatusCodeValue();
        }
    }

    // Inner class to represent the structure of the request body
    private static class OpenAiRequest {

        @JsonProperty("model")
        private String model;

        @JsonProperty("messages")
        private OpenAiMessage[] messages;

        public OpenAiRequest(String model, String features, String transcriptionSnippet) {
            this.model = model;
            this.messages = new OpenAiMessage[] {
                new OpenAiMessage("user", features),
                new OpenAiMessage("user", "Transcription Snippet: \"" + transcriptionSnippet + "\""),
                new OpenAiMessage("user", "Please suggest if we can use any of the above feature based on the transcript provided either to ease current description or next steps. Response should be max two liner without mentioning transcript. Output should be in format: [{suggested feature, suggestion}]")
            };
        }
    }

    // Inner class to represent the structure of each message
    private static class OpenAiMessage {

        @JsonProperty("role")
        private String role;

        @JsonProperty("content")
        private String content;

        public OpenAiMessage(String role, String content) {
            this.role = role;
            this.content = content;
        }
    }
}
