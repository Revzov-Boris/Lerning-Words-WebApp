package com.example.learning_words_app.dto;

import java.util.List;

public record WordViewModel(
        int id,
        int categoryId,
        List<FormWordViewModel> forms
) {
}
