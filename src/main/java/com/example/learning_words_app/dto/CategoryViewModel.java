package com.example.learning_words_app.dto;

import java.util.List;

public record CategoryViewModel(
        int id,
        String name,
        String description,
        int countWords,
        int countForms,
        List<String> formsInfo,
        LanguageViewModel language) {
}
