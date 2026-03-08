package com.substring.quiz.quiz_service.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/config")
@RefreshScope
public class ConfigControllerQuiz {

    @Value("${config.value:default value}")
    private String configValue;

    @GetMapping
    public String configValue(){
        System.out.println("config value is:"+configValue);
        return configValue;
    }
}
