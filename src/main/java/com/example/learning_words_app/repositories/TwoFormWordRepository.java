package com.example.learning_words_app.repositories;

import com.example.learning_words_app.entities.TwoFormWordEntity;
import com.example.learning_words_app.entities.WordEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TwoFormWordRepository extends JpaRepository<TwoFormWordEntity, Integer> {
    @Query("SELECT w FROM TwoFormWordEntity w WHERE w.category.id = :id")
    public List<TwoFormWordEntity> findByCategoryId(@Param("id") Integer id);
}
