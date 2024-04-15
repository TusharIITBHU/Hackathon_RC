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

        this.messages.add(new MessageToOpenAI("user", featureString));
        this.messages.add(new MessageToOpenAI("user", "Transcript: \"" + transcriptionString + "\""));
        this.messages.add(new MessageToOpenAI("user", "Please suggest if we can use any of the above feature based on the transcript provided either to ease current description or next steps. Response should be max five liner without mentioning transcript or user sensitive info. Output should be in format -> featureId : featureName : description of feature"));
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
