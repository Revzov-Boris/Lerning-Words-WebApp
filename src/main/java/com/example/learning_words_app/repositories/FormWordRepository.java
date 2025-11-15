package com.example.learning_words_app.repositories;

import com.example.learning_words_app.entities.FormWordEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface FormWordRepository extends JpaRepository<FormWordEntity, Integer> {
    @Query("""
        SELECT f.audioData
        FROM FormWordEntity f
        WHERE word.id = :wordId
            AND number = :number
        """)
    Optional<byte[]> findAudioDataByWordIdAndNumber(@Param("wordId") int wordId, @Param("number") int number);
}
