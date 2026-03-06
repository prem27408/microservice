package com.substring.quiz.quiz_service;

import com.substring.quiz.quiz_service.dto.CategoryDto;
import com.substring.quiz.quiz_service.service.CategoryFeignService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.openfeign.EnableFeignClients;

import java.util.List;

@SpringBootTest

class QuizServiceApplicationTests {

	@Autowired
	private CategoryFeignService categoryFeignService;

//	@Test
//	public void testFeignAllCategories(){
//    System.out.println("getting all categories");
//    List<CategoryDto> all = categoryFeignService.findAll();
//	all.stream().forEach(categoryDto -> {
//		System.out.println(categoryDto.getTitle());
//	});
//	}

//	@Test
//	public void testFeignSingleCategory(){
//    CategoryDto data = categoryFeignService.findById(2);
//	}

}
