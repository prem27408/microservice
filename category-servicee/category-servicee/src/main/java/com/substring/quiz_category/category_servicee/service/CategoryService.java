package com.substring.quiz_category.category_servicee.service;

import com.substring.quiz_category.category_servicee.dto.CategoryDto;

import java.util.List;

public interface CategoryService {

    CategoryDto createCategory(CategoryDto categoryDto);

    CategoryDto updateCategory(String categoryId, CategoryDto categoryDto);

    CategoryDto getCategory(String categoryId);

    void deleteCategory(String categoryId);

    List<CategoryDto> getAllCategories();


}
