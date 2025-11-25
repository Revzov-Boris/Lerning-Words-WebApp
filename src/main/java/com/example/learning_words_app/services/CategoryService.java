package com.example.learning_words_app.services;

import com.example.learning_words_app.entities.CategoryEntity;
import com.example.learning_words_app.repositories.CategoryRepository;
import com.example.learning_words_app.viewmodels.CategoryViewModel;
import com.example.learning_words_app.viewmodels.LanguageViewModel;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CategoryService {
    @Autowired
    CategoryRepository categoryRepository;

    public static final Map<String, String> ISO_LANGUAGE = Map.of(
            "eng", "английский",
            "deu", "немецкий"
    );

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


    public List<CategoryViewModel> allCategoryByLanguage(String language) {
        return categoryRepository.findByLanguage(language).stream().map(
                e -> new CategoryViewModel(e.getId(), e.getName(), e.getDescription(), e.getWords().size())
        ).toList();
    }


    public List<LanguageViewModel> getAllLanguages() {
        List<String> isoLanguages = categoryRepository.findAllLanguages();
        List<LanguageViewModel> languages = new ArrayList<>();
        for (String iso : isoLanguages) {
            System.out.println("ISO: " + iso);
            LanguageViewModel model = new LanguageViewModel(ISO_LANGUAGE.get(iso), iso);
            languages.add(model);
        }
        return languages;
    }
}
