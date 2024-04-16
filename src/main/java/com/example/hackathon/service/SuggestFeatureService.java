package com.example.hackathon.service;

import com.example.hackathon.dto.request.RequestBodyWithKey;

public interface SuggestFeatureService {
    String getSuggestedFeature(RequestBodyWithKey requestBodyWithKey);

}
