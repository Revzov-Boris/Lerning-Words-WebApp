package com.example.learning_words_app.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "trainings")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TrainingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
    private int status;
    private LocalDateTime createDate;
    private LocalDateTime endDate;
    @OneToMany(mappedBy = "training", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("indexInTraining ASC")
    private List<QuestionEntity> questions;
    private String token;
}
