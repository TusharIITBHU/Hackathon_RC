package com.example.hackathon.controller;

import com.example.hackathon.dto.request.Transcript;
import com.example.hackathon.service.SuggestFeatureServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SuggestFeatureController {

    @Autowired
    SuggestFeatureServiceImpl service;

    @GetMapping("/")
    public String hello() {
        return "Hello, Shaunak and Tushar!";
    }

    @PostMapping("/suggestFeature")
    public ResponseEntity<String> processTest(@RequestBody List<Transcript> transcriptions) {
        String suggestedFeature = service.getSuggestedFeature(transcriptions);
        return new ResponseEntity<>(suggestedFeature, HttpStatus.OK);
    }

}
