package com.example.learning_words_app.dto;

public record ResultQuestionViewModel(
        QuestionViewModel question,
        String userAnswer,
        String rightAnswer,
        boolean isRight
) {
}
