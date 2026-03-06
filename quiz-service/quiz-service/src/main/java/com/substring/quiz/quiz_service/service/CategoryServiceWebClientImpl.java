package com.substring.quiz.quiz_service.service;

import com.substring.quiz.quiz_service.dto.CategoryDto;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.List;

@Service
public class CategoryServiceWebClientImpl implements CategoryService{

    private final RestTemplate restTemplate;
    private final WebClient webClient;
    private final WebClient.Builder webClientBuilder;
    private final ModelMapper modelMapper;
    private final Logger logger= LoggerFactory.getLogger(CategoryService.class);

    public CategoryServiceWebClientImpl(RestTemplate restTemplate, WebClient.Builder webClientBuilder, ModelMapper modelMapper) {
        this.restTemplate = restTemplate;
        this.webClientBuilder = webClientBuilder;
        this.modelMapper=modelMapper;
        this.webClient=webClientBuilder.baseUrl("http://CATEGORY-SERVICEE").build();
    }

    //webclient
    @Override
    public CategoryDto findById(String categoryId) {
        try{
            CategoryDto category =
                    this.webClient
                            .get()
                            .uri("/api/v1/categories/{categoryId}", categoryId)
                            .retrieve()
                            .bodyToMono(CategoryDto.class)
                            .block();

            return  category;
        }catch(WebClientResponseException webClientResponseException){

            if(webClientResponseException.getStatusCode().equals(HttpStatus.NOT_FOUND)){
                logger.error("category not found");
            }else if(webClientResponseException.getStatusCode().equals(HttpStatus.INTERNAL_SERVER_ERROR)){
                logger.error("internet server error");
            }

            webClientResponseException.printStackTrace();
        }
        return null;
    }

    @Override
    public List<CategoryDto> findAll() {
        return this.webClient
                .get()
                .uri("/api/v1/categories")
                .retrieve()
                .bodyToFlux(CategoryDto.class)
                .collectList()
                .block();
    }

    @Override
    public CategoryDto create(CategoryDto categoryDto) {
     return this.webClient.post()
             .uri("/api/v1/categories")
             .bodyValue(categoryDto)
             .retrieve()
             .bodyToMono(CategoryDto.class)
             .block();

    }

    @Override
    public CategoryDto update(String categoryId, CategoryDto categoryDto) {
        return this.webClient.put()
                .uri("/api/v1/categories/{categoryId}",categoryId)
                .bodyValue(categoryDto)
                .retrieve()
                .bodyToMono(CategoryDto.class)
                .block();
    }

    @Override
    public void delete(String categoryId) {
             this.webClient.delete()
                     .uri("/api/v1/categories/{categoryId}",categoryId)
                     .retrieve()
                     .toBodilessEntity()
                     .block();
    }
}
