package com.substring.quiz_category.category_servicee.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/config")
public class ConfigController {

    @Value("${config.value}")
    private String configValue;

    public String configValue(){
    System.out.println("config value is:"+configValue);
    return configValue;
  }
}
