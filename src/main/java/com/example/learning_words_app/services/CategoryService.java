package com.example.learning_words_app.services;

import com.example.learning_words_app.entities.CategoryEntity;
import com.example.learning_words_app.repositories.CategoryRepository;
import com.example.learning_words_app.viewmodels.CategoryViewModel;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CategoryService {
    @Autowired
    CategoryRepository categoryRepository;

    public List<CategoryViewModel> allCategory() {
        return categoryRepository.findAll().stream().map(
                e -> new CategoryViewModel(e.getId(), e.getName(), e.getDescription(), e.getWords().size())
        ).toList();
    }


    public CategoryViewModel getById(int id) {
        CategoryEntity categoryEntity = categoryRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Not found category with id = " + id)
        );
        CategoryViewModel category = new CategoryViewModel(
                categoryEntity.getId(),
                categoryEntity.getName(),
                categoryEntity.getDescription(),
                categoryEntity.getWords().size());
        return category;
    }
}
