package com.example.learning_words_app;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Training {
    Long id;
    List<Question> questions;
    List<String> answers;
    int status;

    /**
     status
     0 - тренировка создана, ответов нет
     1 - тренировка закончена, ответы введены
     */
}
