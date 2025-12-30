package com.example.learning_words_app.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "forms_of_word")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FormWordEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "word_id")
    private WordEntity word;
    private Integer number;
    private String content;
    private String translation;
    private String transcription;
    @Lob
    private byte[] audioData;


    public FormWordEntity(WordEntity word, Integer number, String content, String translation, String transcription, byte[] audioData) {
        this.word = word;
        this.number = number;
        this.content = content;
        this.translation = translation;
        this.transcription = transcription;
        this.audioData = audioData;
    }
}
