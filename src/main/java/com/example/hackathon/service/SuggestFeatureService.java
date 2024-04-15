package com.example.hackathon.service;

import com.example.hackathon.dto.request.Transcript;
import java.util.List;

public interface SuggestFeatureService {
    String getSuggestedFeature(List<Transcript> transcriptions);

}
