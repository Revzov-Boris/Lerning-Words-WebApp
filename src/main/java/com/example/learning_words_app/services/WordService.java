package com.example.learning_words_app.services;

import com.example.learning_words_app.Category;
import com.example.learning_words_app.FormWord;
import com.example.learning_words_app.Word;
import com.example.learning_words_app.entities.FormWordEntity;
import com.example.learning_words_app.entities.WordEntity;
import com.example.learning_words_app.repositories.WordRepository;
import com.example.learning_words_app.viewmodels.FormWordViewModel;
import com.example.learning_words_app.viewmodels.WordViewModel;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;


@Service
public class WordService {
    @Autowired
    WordRepository wordRepository;


    public static Word toWord(WordEntity entity) {
        Word word = new Word();
        word.setId(entity.getId());
        word.setCategory(new Category(entity.getCategory().getId(),
                entity.getCategory().getName(),
                entity.getCategory().getDescription()));
        List<FormWord> forms = new ArrayList<>();
        for (FormWordEntity formEntity : entity.getForms()) {
            forms.add(new FormWord(formEntity.getContent(), formEntity.getTranslation(), formEntity.getTranscription(), formEntity.getAudioData()));
        }
        word.setForms(forms);
        return word;
    }


    public static WordViewModel toWordViewModel(Word word) {
        List<FormWordViewModel> smallForms = new ArrayList<>();
        for (FormWord formWord : word.getForms()) {
            String smallTranslation;
            if (formWord.getTranslation() != null) {
                smallTranslation = formWord.getTranslation().split(" ")[0];
            } else {
                smallTranslation = formWord.getTranslation();
            }
            smallForms.add(new FormWordViewModel(formWord.getContent(), smallTranslation, formWord.getTranscription()));
        }
        return new WordViewModel(word.getId(), word.getCategory().getId(), smallForms);
    }


    public Word getById(Integer id) {
        WordEntity wordEntity = wordRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(String.format("Not found word with id = %d", id))
        );
        return toWord(wordEntity);
    }


    public List<Word> getAllWordByCategory(int id) {
        List<Word> words = new ArrayList<>();
        for (WordEntity entity : wordRepository.findByCategoryId(id)) {
            words.add(toWord(entity));
        }
        return words;
    }


    public List<Word> getAllWordByIds(List<Integer> selectedIds) {
        List<Word> allByIds = wordRepository.findAll().stream().map(e -> toWord(e)).toList();
        return allByIds.stream().filter(w -> selectedIds.contains(w.getId())).toList();
    }


    public WordEntity getEntityById(int id) {
        return wordRepository.findById(id).orElseThrow( () ->
                new EntityNotFoundException("Not found word with id = " + id));
    }


    public List<WordViewModel> getModelsByCategory(int id) {
        return getAllWordByCategory(id).stream().map(
                w -> toWordViewModel(w)
        ).toList();
    }
}
