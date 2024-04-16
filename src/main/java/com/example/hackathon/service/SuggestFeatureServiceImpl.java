package com.example.hackathon.service;

import com.example.hackathon.dto.Feature;
import com.example.hackathon.dto.request.RequestBodyWithKey;
import com.example.hackathon.dto.request.RequestToOpenAI;
import com.example.hackathon.dto.response.ResponseFromOpenAI;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class SuggestFeatureServiceImpl implements SuggestFeatureService{
    private final String OPENAI_API_URL = "https://api.openai.com/v1/chat/completions";
    private final String OPENAI_MODEL = "gpt-3.5-turbo";
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final List<Feature> FEATURES_LIST = Arrays.asList(
        new Feature("whiteboard", "WhiteBoard", "Online whiteboard solution for modern collaboration which enables in-person and remote teams can ideate and brainstorming the system designing/architecture."),
        new Feature("poll_feature", "Poll Feature", "The Poll feature is designed to enhance communication and decision-making during video conferencing sessions or telephone conversations. With this feature, participants can create and conduct polls in real-time to gather opinions, feedback, and preferences from other participants."),
        new Feature("breakout_rooms", "Breakout Rooms", "Breakout Rooms are a versatile feature designed to facilitate focused discussions, collaboration, and brainstorming sessions within larger video conferencing meetings. They enable participants to split into smaller groups, each with its own virtual space, to engage in specific topics, tasks, or discussions.")
    );

    @Autowired
    public SuggestFeatureServiceImpl (RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public String getSuggestedFeature(RequestBodyWithKey requestBodyWithKey) {
        RequestToOpenAI requestToOpenAI;
        try {
            requestToOpenAI = new RequestToOpenAI(OPENAI_MODEL, FEATURES_LIST, requestBodyWithKey.getTranscriptions());
        } catch (JsonProcessingException e) {
            return "Error occurred while parsing the content: " + e.getMessage();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(requestBodyWithKey.getOpenAiKey());

        HttpEntity<RequestToOpenAI> entity = new HttpEntity<>(requestToOpenAI, headers);

        ResponseEntity<String> response;
        try {
            response = restTemplate.exchange(OPENAI_API_URL, HttpMethod.POST, entity, String.class);
        } catch (HttpClientErrorException ex) {
            return "Error occurred while calling OpenAI API: " + ex.getMessage();
        }

        String content = extractContentFromResponse(response.getBody());

        String features = extractFeatures(content);

        if (response.getStatusCode() == HttpStatus.OK) {
            return features;
        } else {
            return "OpenAI API returned response: " + content;
        }
    }

    private String extractContentFromResponse(String jsonResponse) {
        ResponseFromOpenAI responseFromOpenAI;
        try {
            responseFromOpenAI = objectMapper.readValue(jsonResponse, ResponseFromOpenAI.class);
            if (responseFromOpenAI != null && responseFromOpenAI.getChoices() != null && responseFromOpenAI.getChoices().length > 0 && responseFromOpenAI.getChoices()[0].getMessage()!= null) {
                return responseFromOpenAI.getChoices()[0].getMessage().getContent();
            }
        } catch (Exception e) {
            return "Error parsing OpenAI response: " + e.getMessage();
        }
        return jsonResponse;
    }

    private String extractFeatures(String content) {
        List<Feature> features = new ArrayList<>();
        try {
            String[] lines = content.split("\\n");
            for (String line : lines) {
                String[] parts = line.split(": ");
                if (parts.length == 3) {
                    String featureId = parts[0].trim();
                    String featureName = parts[1].trim();
                    String description = parts[2].trim();
                    features.add(new Feature(featureId, featureName, description));
                }
            }

            return objectMapper.writeValueAsString(features);
        } catch (Exception e) {
            return "Error occurred while parsing the content: " + e.getMessage();
        }
    }
}
