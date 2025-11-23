package com.example.learning_words_app;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Category {
    private int id;
    private String name;
    private String description;
    private List<String> formsInfo;
}
