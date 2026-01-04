package com.example.learning_words_app.entities;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "forms_of_word")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class FormWordEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "word_id")
    @ToString.Exclude
    private WordEntity word;
    private Integer number;
    private String content;
    private String translation;
    private String transcription;
    @ToString.Exclude
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


    public FormWordEntity(Integer id, WordEntity word, Integer number, String content, String translation, String transcription) {
        this.id = id;
        this.word = word;
        this.number = number;
        this.content = content;
        this.translation = translation;
        this.transcription = transcription;
    }
}
