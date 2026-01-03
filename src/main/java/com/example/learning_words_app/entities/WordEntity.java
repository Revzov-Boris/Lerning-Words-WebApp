package com.example.learning_words_app.entities;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "words")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class WordEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private CategoryEntity category;
    @OneToMany(mappedBy = "word", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("number ASC")
    @ToString.Exclude
    private List<FormWordEntity> forms;
}
