package com.example.learning_words_app.dto;

public record FormWordViewModel(
    String content,
    String translation,
    String transcription,
    boolean isThereAudio) {
}
