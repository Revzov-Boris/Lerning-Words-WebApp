package com.example.learning_words_app.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FormOfWordForm {
    @NotBlank(message = "слово не может быть пустым")
    @Size(max = 50, message = "слово слишком длинное")
    private String content;
    @NotBlank(message = "перевод не может быть пустым")
    @Size(max = 100, message = "перевод слишком длинный")
    private String translation;
    @NotBlank(message = "транскрипция не может быть пустой")
    @Size(max = 50, message = "транскрипция слишком длинная")
    private String transcription;
}
