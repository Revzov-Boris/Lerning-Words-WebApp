package com.example.learning_words_app.services;

import com.example.learning_words_app.entities.CategoryEntity;
import com.example.learning_words_app.repositories.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    @Autowired
    CategoryRepository categoryRepository;

    public List<CategoryEntity> allCategory() {
        return categoryRepository.findAll();
    }

    public CategoryEntity getById(int id) {
        CategoryEntity categoryEntity = categoryRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Not found category with id = " + id)
        );
        return categoryEntity;
    }
}
