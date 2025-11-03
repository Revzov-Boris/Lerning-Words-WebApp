package com.example.learning_words_app.services;

import com.example.learning_words_app.entities.CategoryEntity;
import com.example.learning_words_app.repositories.CategoryRepository;
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

    public Optional<CategoryEntity> getById(int id) {
        return categoryRepository.findById(id);
    }
}
