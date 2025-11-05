package com.example.learning_words_app.entities;

import com.example.learning_words_app.Question;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "trainings")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TrainingEntity {
    @Id
    private Long id;
    private int status;
    private LocalDate createDate;
    private LocalDate endDate;
    @OneToMany(mappedBy = "training")
    private List<QuestionEntity> questions;
}
