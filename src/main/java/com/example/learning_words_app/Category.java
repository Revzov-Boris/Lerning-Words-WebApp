package com.example.learning_words_app;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Category {
    private int id;
    private String name;
    private String description;
}
