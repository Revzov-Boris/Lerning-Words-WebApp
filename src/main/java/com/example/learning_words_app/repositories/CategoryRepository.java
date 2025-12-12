package com.example.learning_words_app.repositories;

import com.example.learning_words_app.entities.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Integer> {
    @Query("SELECT c FROM CategoryEntity c WHERE language.id = :languageId")
    List<CategoryEntity> findByLanguage(@Param("languageId") Integer languageId);


    @Query("SELECT DISTINCT c.language FROM CategoryEntity c")
    List<String> findAllLanguages();

    List<CategoryEntity> findAllByName(String name);
}
