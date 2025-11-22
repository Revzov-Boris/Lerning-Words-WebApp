package com.example.learning_words_app.viewmodels;

public record FormWordViewModel(
    String content,
    String translation,
    String transcription,
    boolean isThereAudio) {
}
