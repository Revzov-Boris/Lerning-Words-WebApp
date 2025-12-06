package com.example.learning_words_app.dto;


import java.time.LocalDateTime;

public record ProfileInfoViewModel(
        int id,
        String name,
        String timeInTraining,
        int countTrainings,
        LocalDateTime registrationDate
) {

}
