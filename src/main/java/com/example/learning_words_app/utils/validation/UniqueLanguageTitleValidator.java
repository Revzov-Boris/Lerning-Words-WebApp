package com.example.learning_words_app.utils.validation;

import com.example.learning_words_app.repositories.LanguageRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UniqueLanguageTitleValidator implements ConstraintValidator<UniqueLanguageTitle, String> {
    private final LanguageRepository languageRepository;

    public UniqueLanguageTitleValidator(LanguageRepository languageRepository) {
        this.languageRepository = languageRepository;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isBlank()) {
            return true;
        }
        return languageRepository.findByTitle(value).isEmpty();
    }
}
