package com.example.learning_words_app.repositories;

import com.example.learning_words_app.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    @Query("""
    SELECT u
    FROM UserEntity u
    WHERE u.nickname = :nick
    """)
    Optional<UserEntity> findByNickname(@Param("nick") String login);

}
