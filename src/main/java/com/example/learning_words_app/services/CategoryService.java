package com.example.learning_words_app.services;

import com.example.learning_words_app.dto.CategoryAddForm;
import com.example.learning_words_app.entities.CategoryEntity;
import com.example.learning_words_app.entities.LanguageEntity;
import com.example.learning_words_app.repositories.CategoryRepository;
import com.example.learning_words_app.dto.CategoryViewModel;
import com.example.learning_words_app.repositories.LanguageRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CategoryService {
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    LanguageRepository languageRepository;


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


    public List<CategoryViewModel> allCategoryByLanguage(Integer language) {
        return categoryRepository.findByLanguage(language).stream().map(
                e -> new CategoryViewModel(e.getId(), e.getName(), e.getDescription(), e.getWords().size())
        ).toList();
    }


    public void deleteCategoryById(Integer categoryId) {
        categoryRepository.deleteById(categoryId);
        System.out.println("Категория " + categoryId + " удалена");
    }


    public boolean isUniqueInLanguage(String name, int languageId) {
        List<CategoryEntity> entities = categoryRepository.findAllByName(name);
        if (entities.isEmpty()) {
            return true;
        }
        for (CategoryEntity c : entities) {
            if (c.getLanguage().getId() == languageId) {
                System.out.println(c);
                return false;
            }
        }
        return true;
    }

    public void createCategory(CategoryAddForm form, int languageId) {
        LanguageEntity languageEntity = languageRepository.findById(languageId).orElseThrow(
                () -> new EntityNotFoundException("Not found languge")
        );
        CategoryEntity entity = new CategoryEntity(languageEntity, form.name(), form.description(), form.countForms());
        categoryRepository.save(entity);
    }
}
