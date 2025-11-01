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
@Table(name = "two_form_words")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TwoFormWordEntity implements MayBeWord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String content;
    private String translation;
    private String transcription;
    private String secondForm;
    private String secondFormTranslation;
    private String secondFormTranscription;
    @Lob
    private byte[] contentAudioData;
    @Lob
    private byte[] secondFormAudioData;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private CategoryEntity category;


    public TwoFormWordEntity(String content, String translation, String transcription, String secondForm, String secondFormTranslation, String secondFormTranscription, CategoryEntity category) {
        this.content = content;
        this.translation = translation;
        this.transcription = transcription;
        this.secondForm = secondForm;
        this.secondFormTranslation = secondFormTranslation;
        this.secondFormTranscription = secondFormTranscription;
        this.category = category;
    }

    @Override
    public Word toWord() {
        List<FormWord> listForms = List.of(new FormWord(content, translation, transcription, contentAudioData),
                                           new FormWord(secondForm, secondFormTranslation, secondFormTranscription, secondFormAudioData));
        return new Word(id, category.getId(), listForms);
    }
}
