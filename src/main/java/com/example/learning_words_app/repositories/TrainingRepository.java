package com.example.learning_words_app.repositories;

import com.example.learning_words_app.entities.TrainingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TrainingRepository extends JpaRepository<TrainingEntity, Long> {
    @Query("""
    SELECT COUNT(q)
    FROM QuestionEntity q
    WHERE q.training.status = 1
        AND q.training.user.nickname = :userName
        AND q.word.id = :wordId
    """)
    long getCountQuestionWithWordAndUser(@Param("wordId") Integer wordId, @Param("userName") String userName);


    @Query("""
    SELECT COUNT(q)
    FROM QuestionEntity q
    WHERE q.training.status = 1
        AND q.training.user.nickname = :userName
        AND q.word.id = :wordId
        AND q.isRight = true
    """)
    long getCountRightAnswersWithWordAndUser(@Param("wordId") Integer wordId, @Param("userName") String userName);
}
