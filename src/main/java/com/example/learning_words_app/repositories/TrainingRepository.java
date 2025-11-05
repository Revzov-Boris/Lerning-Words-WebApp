package com.example.learning_words_app.repositories;

import com.example.learning_words_app.entities.TrainingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrainingRepository extends JpaRepository<TrainingEntity, Long> {
}
