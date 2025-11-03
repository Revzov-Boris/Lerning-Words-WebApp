package com.example.learning_words_app.repositories;

import com.example.learning_words_app.entities.ThreeFormWordEntity;
import com.example.learning_words_app.entities.TwoFormWordEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ThreeFormWordRepository extends JpaRepository<ThreeFormWordEntity, Integer> {
    @Query("SELECT w FROM ThreeFormWordEntity w WHERE w.category.id = :id")
    public List<ThreeFormWordEntity> findByCategoryId(@Param("id") Integer id);
}
