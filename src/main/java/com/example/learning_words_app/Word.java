package com.example.learning_words_app;

import lombok.*;

import java.util.List;

@Setter @Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Word {
    private Integer id;
    private Category category;
    private List<FormWord> forms;
}
