package com.example.learning_words_app.viewmodels;

import com.example.learning_words_app.Question;


public record ResultQuestionViewModel(
        Question question,
        String userAnswer,
        boolean isRight
) {
}
