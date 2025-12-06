package com.example.learning_words_app.dto;

public record QuestionViewModel (
        WordViewModel word,
        int type,
        String text,
        boolean hasAudio,
        int formIndex
){
}
