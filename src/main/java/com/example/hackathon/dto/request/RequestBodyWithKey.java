package com.example.hackathon.dto.request;

import java.util.List;

public class RequestBodyWithKey {
    private List<Transcript> transcriptions;
    private String openAiKey;

    public List<Transcript> getTranscriptions() {
        return transcriptions;
    }

    public void setTranscriptions(List<Transcript> transcriptions) {
        this.transcriptions = transcriptions;
    }

    public String getOpenAiKey() {
        return openAiKey;
    }

    public void setOpenAiKey(String openAiKey) {
        this.openAiKey = openAiKey;
    }
}
