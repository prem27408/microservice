package com.substring.quiz.quiz_service.service;

import com.substring.quiz.quiz_service.collections.Quiz;
import com.substring.quiz.quiz_service.dto.CategoryDto;
import com.substring.quiz.quiz_service.dto.QuizDto;
import com.substring.quiz.quiz_service.repositories.QuizRepositories;
import feign.FeignException;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.List;
import java.util.UUID;

@Service
public class QuizeServiceImpl implements QuizService{

    private QuizRepositories quizRepositories;
    private ModelMapper modelMapper;
    private Logger logger= LoggerFactory.getLogger(QuizeServiceImpl.class);
    private final RestTemplate restTemplate;
    private final CategoryServiceWebClientImpl categoryService;
    private final CategoryFeignService categoryFeignService;

    public QuizeServiceImpl(CategoryServiceWebClientImpl categoryService, ModelMapper modelMapper, QuizRepositories quizRepositories, RestTemplate restTemplate,CategoryFeignService categoryFeignService) {

        this.categoryService = categoryService;
        this.modelMapper = modelMapper;
        this.quizRepositories = quizRepositories;
        this.restTemplate = restTemplate;
        this.categoryFeignService=categoryFeignService;
    }

    @Override
    public QuizDto create(QuizDto quizDto) {
        Quiz quiz = modelMapper.map(quizDto, Quiz.class);
        quiz.setId(UUID.randomUUID().toString());
        String url="http://CATEGORY-SERVICEE/api/v1/categories/"+quizDto.getCategoryId();
        logger.info(url);
        CategoryDto category = restTemplate.getForObject(url, CategoryDto.class);
        Quiz savedQuiz = quizRepositories.save(quiz);
        QuizDto quizDto1 = modelMapper.map(savedQuiz, QuizDto.class);
        quizDto1.setCategoryDto(category);
        return quizDto1;
    }

    @Override
    public QuizDto update(String quizId, QuizDto quizDto) {
        Quiz quiz = quizRepositories.findById(quizId).orElseThrow(() -> new RuntimeException("quiz noot found"));
        quiz.setTitle(quizDto.getTitle());
        quiz.setDescription(quizDto.getDescription());
        quiz.setMaxMarks(quizDto.getMaxMarks());
        quiz.setTimeLimit(quizDto.getTimeLimit());
        quiz.setCreatedBy(quizDto.getCreatedBy());
        quiz.setNoOfQuestions(quizDto.getNoOfQuestions());
        quiz.setImageUrl(quizDto.getImageUrl());
        quiz.setLive(quizDto.getLive());
        quiz.setPassingMarks(quizDto.getPassingMarks());
        quiz.setCategoryId(quizDto.getCategoryId());

        Quiz savedQiz = quizRepositories.save(quiz);
        return modelMapper.map(savedQiz, QuizDto.class);
    }

    @Override
    public void delete(String quizId) {
        Quiz quiz = quizRepositories.findById(quizId).orElseThrow(() -> new RuntimeException("quiz noot found"));
         quizRepositories.delete(quiz);
    }

    @Override
    public List<QuizDto> findAll() {
        List<Quiz> quizes = quizRepositories.findAll();
    // getting categories of all quiz
//    List<QuizDto> quizDtos =
//        quizes.stream()
//            .map(
//                quiz -> {
//                  String categoryId = quiz.getCategoryId();
//                    QuizDto quizDto = modelMapper.map(quiz, QuizDto.class);
//                  //call to quizservice using webclient
//                    try{
//                  CategoryDto category =
//                      this.webClient
//                          .get()
//                          .uri("/api/v1/categories/{categoryId}", categoryId)
//                          .retrieve()
//                          .bodyToMono(CategoryDto.class)
//                          .block();
//
//                  quizDto.setCategoryDto(category);
//                  }catch(WebClientResponseException webClientResponseException){
//
//                        if(webClientResponseException.getStatusCode().equals(HttpStatus.NOT_FOUND)){
//                            logger.error("category not found");
//                        }else if(webClientResponseException.getStatusCode().equals(HttpStatus.INTERNAL_SERVER_ERROR)){
//                            logger.error("internat server error");
//                        }
//                        quizDto.setCategoryDto(null);
//                        webClientResponseException.printStackTrace();
//                    }
//                  return quizDto;
//                })
//            .toList();
//        List<QuizDto> quizDtos=quizes.stream().map(quiz -> {
//            String categoryId=quiz.getCategoryId();
//            QuizDto quizDto = modelMapper.map(quiz, QuizDto.class);
//            CategoryDto categoryDto = this.categoryService.findById(categoryId);
//            quizDto.setCategoryDto(categoryDto);
//            return quizDto;
//        }).toList();

        List<QuizDto> quizDtos=quizes.stream().map(quiz -> {
            String categoryId=quiz.getCategoryId();
            QuizDto quizDto = modelMapper.map(quiz, QuizDto.class);
            CategoryDto categoryDto=null;
            try{
                categoryDto = this.categoryFeignService.findById(categoryId);
            }catch (FeignException.NotFound ex) {
                logger.error("Category not found for id: {}", quiz.getCategoryId());
            }
            quizDto.setCategoryDto(categoryDto);
            return quizDto;
        }).toList();


    return quizDtos;
    }

//    @Override
//    public QuizDto findById(String quizId) {
//        Quiz quiz = quizRepositories.findById(quizId).orElseThrow(() -> new RuntimeException("quiz noot found"));
//        QuizDto quizDto = modelMapper.map(quiz, QuizDto.class);
//        String categoryId = quiz.getCategoryId();
//        String url="http://CATEGORY-SERVICEE/api/v1/categories/"+categoryId;
//        logger.info(url);
//        CategoryDto category = restTemplate.getForObject(url, CategoryDto.class);
//        quizDto.setCategoryDto(category);
//        return quizDto;
//    }

    @Override
    public QuizDto findById(String quizId) {

        Quiz quiz = quizRepositories.findById(quizId)
                .orElseThrow(() -> new RuntimeException("Quiz not found"));

        QuizDto quizDto = modelMapper.map(quiz, QuizDto.class);

        CategoryDto categoryDto = null;

        try {
            categoryDto = categoryFeignService.findById(quiz.getCategoryId());
        } catch (FeignException.NotFound ex) {
            logger.error("Category not found for id: {}", quiz.getCategoryId());
        }
        quizDto.setCategoryDto(categoryDto);
        return quizDto;
    }

    @Override
    public List<QuizDto> findByCategoryId(String categoryId) {
        List<Quiz> quizes = quizRepositories.findByCategoryId(categoryId);
        return quizes.stream().map(quiz->{
            QuizDto quizDto = modelMapper.map(quiz, QuizDto.class);
            CategoryDto categoryDto=null;
            try{
             categoryDto = categoryFeignService.findById(quizDto.getCategoryId());
            }catch(FeignException.NotFound ex){
                 logger.error("category not found");
            }
            quizDto.setCategoryDto(categoryDto);
            return quizDto;
        }).toList();
    }
}
