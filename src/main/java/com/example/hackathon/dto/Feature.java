package com.example.hackathon.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Feature {
    @JsonProperty("featureId")
    private String featureId;
    @JsonProperty("featureName")
    private String featureName;
    @JsonProperty("description")
    private String description;

    public Feature(String featureId, String featureName, String description) {
        this.featureId = featureId;
        this.featureName = featureName;
        this.description = description;
    }
    public String getFeatureId() {
        return featureId;
    }

    public void setFeatureId(String featureId) {
        this.featureId = featureId;
    }

    public String getFeatureName() {
        return featureName;
    }

    public void setFeatureName(String featureName) {
        this.featureName = featureName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
