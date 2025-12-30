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
import java.util.Arrays;
import java.util.List;

@Service
public class CategoryService {
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    LanguageRepository languageRepository;


    public List<CategoryViewModel> allCategory() {
        return categoryRepository.findAll().stream().map(
                e -> toCategoryViewModel(e)
        ).toList();
    }


    public CategoryViewModel getById(int id) {
        CategoryEntity categoryEntity = categoryRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Not found category with id = " + id)
        );
        return toCategoryViewModel(categoryEntity);
    }


    public List<CategoryViewModel> allCategoryByLanguage(Integer language) {
        return categoryRepository.findByLanguage(language).stream().map(
                e -> toCategoryViewModel(e)
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
        String info = null;
        if (!form.formsInfo().isBlank()) {
            info = form.formsInfo();
        }
        CategoryEntity entity = new CategoryEntity(languageEntity, form.name(), form.description(), form.countForms(), info);
        categoryRepository.save(entity);
    }


    public static CategoryViewModel toCategoryViewModel(CategoryEntity entity) {
        List<String> formsInfo = null;
        if (entity.getFormsInfo() != null) {
            formsInfo =  Arrays.asList(entity.getFormsInfo().split("; "));
        }
        return new CategoryViewModel(entity.getId(), entity.getName(),
                entity.getDescription(), entity.getWords().size(),
                entity.getCountForms(), formsInfo
        );
    }


    public static boolean isValidFormsInfo(String formsInfo, int countForms) {
        System.out.println("инфа о формах: " + formsInfo);
        System.out.println();
        if (formsInfo.isEmpty()) return true;
        if (formsInfo.isBlank()) return false;
        return formsInfo.split("; ").length == countForms;
    }


    public static String getErrorCodeFormsInfo(String formsInfo, int countForms) {
        if (!formsInfo.isEmpty() && formsInfo.isBlank()) {
            return "blank.categoryAddForm.formsInfo";
        }
        if (!formsInfo.isEmpty() && formsInfo.split("; ").length != countForms) {
            return "invalidCount.categoryAddForm.formsInfo";
        }
        return null;
    }


    public static String getErrorMessageFormsInfo(String code) {
        return switch (code) {
            case "blank.categoryAddForm.formsInfo" -> "информация не должна состоять только из пробелов";
            case "invalidCount.categoryAddForm.formsInfo" -> "количество форм не совпадает с количеством их названий";
            default -> null;
        };
    }
}
