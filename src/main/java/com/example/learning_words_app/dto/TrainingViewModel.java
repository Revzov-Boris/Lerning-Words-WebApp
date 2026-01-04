package com.example.learning_words_app.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TrainingViewModel {
    private long id;
    private LocalDateTime start;
    private LocalDateTime finish;
    private CategoryViewModel category;
    private int status;
}
