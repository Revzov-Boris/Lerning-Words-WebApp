package com.example.learning_words_app.dto;

public record PersonalWordInfoView(
        WordViewModel word,
        long countQuestions,
        long countRightAnswers
) {
}
