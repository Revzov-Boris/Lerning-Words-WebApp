package com.example.learning_words_app.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true)
    private String nickname;
    private String hashPass;
    private LocalDateTime timeRegistration;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<TrainingEntity> trainings;


    public UserEntity(String nickname, String hashPass, LocalDateTime dateReg) {
        this.nickname = nickname;
        this.hashPass = hashPass;
        this.timeRegistration = dateReg;
    }
}
