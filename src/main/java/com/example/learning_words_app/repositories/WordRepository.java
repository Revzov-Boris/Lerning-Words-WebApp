package com.example.learning_words_app.repositories;

import com.example.learning_words_app.entities.WordEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;


public interface WordRepository extends JpaRepository<WordEntity, Integer> {
    @Query("SELECT w FROM WordEntity w WHERE category.id = :id")
    List<WordEntity> findByCategoryId(@Param("id") Integer id);
}
