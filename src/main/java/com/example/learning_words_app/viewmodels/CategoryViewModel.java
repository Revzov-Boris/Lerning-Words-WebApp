package com.example.learning_words_app.viewmodels;

public record CategoryViewModel(
    int id,
    String name,
    String description,
    int countWords) {
}
