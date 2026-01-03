package com.example.learning_words_app;

import com.example.learning_words_app.dto.FormOfWordForm;
import com.example.learning_words_app.entities.CategoryEntity;
import com.example.learning_words_app.entities.FormWordEntity;
import com.example.learning_words_app.entities.WordEntity;
import com.example.learning_words_app.services.WordService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;


@SpringBootTest
public class UniqueWordTest {
    static List<FormOfWordForm> forms1 = List.of(
            new FormOfWordForm("go", "идти", "gou"),
            new FormOfWordForm("went", "шёл", "vent")
    );
    static List<FormOfWordForm> forms2 = List.of(
            new FormOfWordForm("go", "пойти", "gou"),
            new FormOfWordForm("went", "шёл", "vent")
    );
    static List<FormOfWordForm> forms3 = List.of(
            new FormOfWordForm("see", "видеть", "si"),
            new FormOfWordForm("saw", "видел", "so")
    );
    static WordEntity word1 = new WordEntity();
    static WordEntity word2 = new WordEntity();
    static {
        word1.setId(1000);
        word1.setForms(List.of(
                new FormWordEntity(399, new WordEntity(2, new CategoryEntity(), List.of(new FormWordEntity())), 1, "go", "идти", "gou"),
                new FormWordEntity(400, new WordEntity(2, new CategoryEntity(), List.of(new FormWordEntity())), 2, "went", "шёл", "vent"))
        );

        word2.setId(1001);
        word2.setForms(List.of(
                new FormWordEntity(100, new WordEntity(3, new CategoryEntity(), List.of(new FormWordEntity())), 1, "see", "видеть", "si"),
                new FormWordEntity(101, new WordEntity(3, new CategoryEntity(), List.of(new FormWordEntity())), 2, "saw", "видел", "so"))
        );
    }
    static List<WordEntity> wordEntities = List.of(word1, word2);


    @Autowired
    private WordService wordService;


    @Test
    void testMethod() {
        Assertions.assertEquals(1000, wordService.isWordUniqueAmongEntities(forms1, wordEntities));
        Assertions.assertEquals(null, wordService.isWordUniqueAmongEntities(forms2, wordEntities));
        Assertions.assertEquals(1001, wordService.isWordUniqueAmongEntities(forms3, wordEntities));
    }
}
