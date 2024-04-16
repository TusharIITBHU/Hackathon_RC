package com.example.hackathon.dto.request;

import com.example.hackathon.dto.Feature;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

public class RequestToOpenAI {

    @JsonProperty("model")
    private String model;

    @JsonProperty("messages")
    private List<MessageToOpenAI> messages;

    private ObjectMapper objectMapper = new ObjectMapper();

    public RequestToOpenAI(String model, List<Feature> features, List<Transcript> transcriptions)
    throws JsonProcessingException {
        this.model = model;
        this.messages = new ArrayList<>();

        String featureString = objectMapper.writeValueAsString(features);
        String transcriptionString = objectMapper.writeValueAsString(transcriptions);

        this.messages.add(new MessageToOpenAI("user", "Hi. You are my assistant"));
        this.messages.add(new MessageToOpenAI("user", "These are the tools available during a meeting: " + featureString));
        this.messages.add(new MessageToOpenAI("user", "This is transcript from a meeting which is currently going on. Based on the context of the discussion, can you suggest any tool mentioned above which will be useful for the meeting participant in the current meeting or the upcoming meetings on the same discussion topic."));
        this.messages.add(new MessageToOpenAI("user", transcriptionString));
        this.messages.add(new MessageToOpenAI("user", "Suggestion should be in format -> featureId : featureName: suggestion text. Suggestion text should not look like its based on the transcript provided. It should be a one liner. Please suggest the most useful tool. Suggest a tool only once during the meeting"));
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public List<MessageToOpenAI> getMessages() {
        return messages;
    }

    public void setMessages(List<MessageToOpenAI> messages) {
        this.messages = messages;
    }
}
