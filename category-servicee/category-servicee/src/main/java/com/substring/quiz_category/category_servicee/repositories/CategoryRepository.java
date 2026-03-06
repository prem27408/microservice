package com.substring.quiz_category.category_servicee.repositories;

import com.substring.quiz_category.category_servicee.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category,String> {
}
