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
@Table(name = "three_form_words")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ThreeFormWordEntity implements MayBeWord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String content;
    private String translation;
    private String transcription;
    private String secondForm;
    private String secondFormTranslation;
    private String secondFormTranscription;
    private String thirdForm;
    private String thirdFormTranslation;
    private String thirdFormTranscription;
    @Lob
    private byte[] contentAudioData;
    @Lob
    private byte[] secondFormAudioData;
    @Lob
    private byte[] thirdFormAudioData;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private CategoryEntity category;


    public ThreeFormWordEntity(String content, String translation, String transcription, String secondForm, String secondFormTranslation, String secondFormTranscription, String thirdForm, String thirdFormTranslation, String thirdFormTranscription, CategoryEntity category) {
        this.content = content;
        this.translation = translation;
        this.transcription = transcription;
        this.secondForm = secondForm;
        this.secondFormTranslation = secondFormTranslation;
        this.secondFormTranscription = secondFormTranscription;
        this.thirdForm = thirdForm;
        this.thirdFormTranslation = thirdFormTranslation;
        this.thirdFormTranscription = thirdFormTranscription;
        this.category = category;
    }

    @Override
    public Word toWord() {
        List<FormWord> listForms = List.of(new FormWord(content, translation, transcription, contentAudioData),
                new FormWord(secondForm, secondFormTranslation, secondFormTranscription, secondFormAudioData),
                new FormWord(thirdForm, thirdFormTranslation, thirdFormTranscription, thirdFormAudioData));
        return new Word(id, category.getId(), listForms);
    }
}
