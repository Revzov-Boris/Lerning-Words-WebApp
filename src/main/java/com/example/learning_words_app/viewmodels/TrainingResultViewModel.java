package com.example.learning_words_app.viewmodels;

import java.util.List;

public record TrainingResultViewModel(
    String timeTraining,
    int countRightAnswers,
    List<ResultQuestionViewModel> questionResults) {
}
