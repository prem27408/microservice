package com.substring.quiz.quiz_service.service;

import com.substring.quiz.quiz_service.dto.CategoryDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@FeignClient(name = "CATEGORY-SERVICEE")
public interface CategoryFeignService {

    @GetMapping("/api/v1/categories")
    List<CategoryDto> findAll();

    //get single category
    @GetMapping("/api/v1/categories/{categoryId}")
    CategoryDto findById(@PathVariable String categoryId);

    @PostMapping("/api/v1/categories")
    CategoryDto create(@RequestBody CategoryDto categoryDto);

    @PutMapping("/api/v1/categories/{categoryId}")
    CategoryDto update(@PathVariable String categoryId,@RequestBody CategoryDto categoryDto);

    @DeleteMapping("/api/v1/categories/{categoryId}")
    void delete(@PathVariable String categoryId);
}
