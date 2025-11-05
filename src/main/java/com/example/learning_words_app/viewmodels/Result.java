package com.example.learning_words_app.viewmodels;

import java.util.List;

public record Result(
        int goodAnswers,
        List<List<String>> answersAndQuestions
) {
}
