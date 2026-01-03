package com.example.learning_words_app.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FormOfWordForm {
    @NotNull(message = "слово должно быть")
    @NotBlank(message = "слово не может быть пустым")
    @Size(max = 50, message = "слово слишком длинное")
    private String content;
    @NotNull(message = "перевод должен быть")
    @NotBlank(message = "перевод не может быть пустым")
    @Size(max = 100, message = "перевод слишком длинный")
    private String translation;
    @NotNull(message = "транскрипция должна быть")
    @NotBlank(message = "транскрипция не может быть пустой")
    @Size(max = 50, message = "транскрипция слишком длинная")
    private String transcription;
    private MultipartFile audioData;


    public FormOfWordForm(String content, String translation, String transcription) {
        this.content = content;
        this.translation = translation;
        this.transcription = transcription;
    }
}
