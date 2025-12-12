package com.example.learning_words_app.services;

import com.example.learning_words_app.dto.LanguageAddForm;
import com.example.learning_words_app.dto.LanguageViewModel;
import com.example.learning_words_app.entities.LanguageEntity;
import com.example.learning_words_app.repositories.LanguageRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LanguageService {
    @Autowired
    private LanguageRepository languageRepository;


    public List<LanguageViewModel> getAll() {
        return languageRepository.findAll().stream().map(
                entity -> toViewModel(entity)).toList(
        );
    }


    public static LanguageViewModel toViewModel(LanguageEntity entity) {
        return new LanguageViewModel(entity.getId(), entity.getTitle());
    }


    public void createLanguage(LanguageAddForm form) {
        LanguageEntity languageEntity = new LanguageEntity(form.title());
        languageRepository.save(languageEntity);
    }


    public void deleteLanguageById(Integer languageId) {
        LanguageEntity entity = languageRepository.findById(languageId).orElseThrow(
                () -> new EntityNotFoundException("Not found language wiht id = " + languageId)
        );
        languageRepository.delete(entity);
    }
}
