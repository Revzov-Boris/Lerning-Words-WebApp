package com.example.learning_words_app.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record CategoryAddForm (
        @NotNull
        @NotBlank(message = "название должно быть")
        @NotEmpty(message = "название не должно быть пустым")
        String name,
        @NotNull
        @NotBlank(message = "описание должно быть")
        @NotEmpty(message = "описание не должно быть пустым")
        String description,
        @Min(value = 1, message = "Количество форм не может быть меньше 1")
        int countForms,
        String formsInfo
){
}
