package com.example.learning_words_app.repositories;

import com.example.learning_words_app.entities.LanguageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LanguageRepository extends JpaRepository<LanguageEntity, Integer> {
}
