package com.example.learning_words_app.viewmodels;

public record QuestionViewModel (
        WordViewModel word,
        int type,
        String text
){
}
