package com.example.learning_words_app.repositories;

import com.example.learning_words_app.entities.TrainingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

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

    @Modifying
    @Transactional
    @Query("""
     DELETE FROM TrainingEntity t
     WHERE t.id IN (
        SELECT DISTINCT q.training.id
        FROM QuestionEntity q
        WHERE q.word.id = :wordId
     )
     """
    )
    void deleteIfHasWord(@Param("wordId") Integer wordId);
}
