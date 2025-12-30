package com.example.learning_words_app.dto;

import jakarta.validation.Valid;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class WrapperOfFormsOfWord {
    private List<FormOfWordForm> list;
}
