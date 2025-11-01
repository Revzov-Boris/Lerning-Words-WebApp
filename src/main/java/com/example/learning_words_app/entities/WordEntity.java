package com.example.learning_words_app.entities;

import com.example.learning_words_app.FormWord;
import com.example.learning_words_app.MayBeWord;
import com.example.learning_words_app.Word;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "words")
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class WordEntity implements MayBeWord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String content;
    private String translation;
    private String transcription;
    @Lob
    private byte[] audioData;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private CategoryEntity category;

    public WordEntity(String content, String translation, String transcription, CategoryEntity category) {
        this.content = content;
        this.translation = translation;
        this.transcription = transcription;
        this.category = category;
    }

    @Override
    public String toString() {
        return String.format("%s — %s (%s)", content, translation, category.getName());
    }

    @Override
    public Word toWord() {
        return new Word(id, category.getId(), List.of(new FormWord(content, translation, transcription, audioData)));
    }
}
