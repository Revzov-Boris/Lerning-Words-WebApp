package com.example.learning_words_app.dto;

import com.example.learning_words_app.utils.validation.UniqueLanguageTitle;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record LanguageAddForm(
    @NotNull(message = "Название не может быть Null")
    @NotEmpty(message = "Название не может быть пустым")
    @UniqueLanguageTitle
    String title
) {
}
