package com.substring.quiz.quiz_service.controllers;

import com.substring.quiz.quiz_service.dto.QuizDto;
import com.substring.quiz.quiz_service.service.QuizService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/quizes")
public class QuizController {

    private final QuizService quizService;

    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @PostMapping
    public ResponseEntity<QuizDto> create(@RequestBody QuizDto quizDto){
        return new ResponseEntity<>(quizService.create(quizDto), HttpStatus.CREATED);
    }

    @PutMapping("/{quizId}")
    public ResponseEntity<QuizDto> update(@PathVariable String quizId, @RequestBody QuizDto quizDto){
      return new ResponseEntity<>(this.quizService.update(quizId,quizDto),HttpStatus.OK );
    }

    @DeleteMapping("/{quizId}")
    public ResponseEntity<QuizDto> delete(@PathVariable String quizId){
        quizService.delete(quizId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<QuizDto>> findAll(){
        return new ResponseEntity<>(this.quizService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{quizId}")
    public ResponseEntity<QuizDto> findById(@PathVariable String quizId){
        return new ResponseEntity<>(this.quizService.findById(quizId), HttpStatus.OK);
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<QuizDto>> findCategory(@PathVariable String categoryId){
        return new ResponseEntity<>(this.quizService.findByCategoryId(categoryId), HttpStatus.OK);
    }




}
