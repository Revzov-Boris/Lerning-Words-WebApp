package com.example.learning_words_app.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "questions")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QuestionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "word_id")
    private WordEntity word;
    @ManyToOne
    @JoinColumn(name = "training_id")
    private TrainingEntity training;
    private int type;
    private String answer; // то, что пользователь ввёл
    private int indexInTraining;


    @Override
    public String toString() {
        return String.format("word: %s; type: %s; index: %s", word.getId(), type, indexInTraining);
    }
}
