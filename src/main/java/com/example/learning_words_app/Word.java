package com.example.learning_words_app;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter @Getter
@AllArgsConstructor
@NoArgsConstructor
public class Word {
    private Integer id;
    private Integer categoryId;
    private List<FormWord> forms;
}
