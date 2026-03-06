package com.substring.quiz.quiz_service.service;

import com.substring.quiz.quiz_service.collections.Quiz;
import com.substring.quiz.quiz_service.dto.QuizDto;

import java.util.List;

public interface QuizService {

 QuizDto create(QuizDto quizDto);

 QuizDto update(String quizId, QuizDto quizDto);

 void delete(String quizId);

 List<QuizDto> findAll();

 QuizDto findById(String quizId);

 List<QuizDto> findByCategoryId(String categoryId);
}
