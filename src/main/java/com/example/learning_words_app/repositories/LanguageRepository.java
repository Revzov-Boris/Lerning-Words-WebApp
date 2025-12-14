package com.example.learning_words_app.repositories;

import com.example.learning_words_app.entities.LanguageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface LanguageRepository extends JpaRepository<LanguageEntity, Integer> {
    Optional<LanguageEntity> findByTitle(String value);
}
