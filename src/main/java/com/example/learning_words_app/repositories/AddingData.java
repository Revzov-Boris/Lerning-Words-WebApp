package com.example.learning_words_app.repositories;

import com.example.learning_words_app.entities.CategoryEntity;
import com.example.learning_words_app.entities.WordEntity;

import java.util.ArrayList;
import java.util.List;

public class AddingData {
    public static void main(String[] args) {
        CategoryEntity category = new CategoryEntity();
        category.setName("неправильные глаголы");
        category.setDescription("глаголы с нестандартными формами");

        List<WordEntity> words = new ArrayList<>();

//        words.add(new WordEntity("go", "ходить", category));
//        words.add(new WordEntity("live", "жить", category));
//        words.add(new WordEntity("work", "работать", category));


    }
}
