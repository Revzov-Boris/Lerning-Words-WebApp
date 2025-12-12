package com.example.learning_words_app.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "categories", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"name", "language_id"})
})
public class CategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "language_id")
    private LanguageEntity language;

    private String name;
    private String description;
    private int countForms;
    private String formsInfo;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "category")
    private List<WordEntity> words;


    @Override
    public String toString() {
        return String.format("%s (%s)", name, description);
    }


    public CategoryEntity(LanguageEntity language, String name, String description, int countForms) {
        this.language = language;
        this.name = name;
        this.description = description;
        this.countForms = countForms;
    }
}
