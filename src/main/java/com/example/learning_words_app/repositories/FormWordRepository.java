package com.example.learning_words_app.repositories;

import com.example.learning_words_app.entities.FormWordEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FormWordRepository extends JpaRepository<FormWordEntity, Integer> {
}
