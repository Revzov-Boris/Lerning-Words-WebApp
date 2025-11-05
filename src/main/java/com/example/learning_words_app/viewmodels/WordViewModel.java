package com.example.learning_words_app.viewmodels;

import java.util.List;

public record WordViewModel(
        int id,
        int categoryId,
        List<FormWordViewModel> forms
) {
}
