package com.example.learning_words_app.dto;

import com.example.learning_words_app.utils.validation.UniqueNickname;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RegistrationForm(
    @NotNull(message = "Ник не может быть Null")
    @NotEmpty(message = "Ник не может быть пустым")
    @UniqueNickname
    @Size(min = 3, max = 30, message = "Длина ника должна быть от 3 до 30 символов")
    String nickname,

    @NotNull
    @NotEmpty
    String password) {
}
