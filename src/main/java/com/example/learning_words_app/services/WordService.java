package com.example.learning_words_app.services;

import com.example.learning_words_app.Category;
import com.example.learning_words_app.FormWord;
import com.example.learning_words_app.Word;
import com.example.learning_words_app.dto.*;
import com.example.learning_words_app.entities.CategoryEntity;
import com.example.learning_words_app.entities.FormWordEntity;
import com.example.learning_words_app.entities.WordEntity;
import com.example.learning_words_app.repositories.CategoryRepository;
import com.example.learning_words_app.repositories.TrainingRepository;
import com.example.learning_words_app.repositories.WordRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Service
public class WordService {
    @Autowired
    WordRepository wordRepository;
    @Autowired
    TrainingRepository trainingRepository;
    @Autowired
    CategoryRepository categoryRepository;


    public static Word toWord(WordEntity entity) {
        Word word = new Word();
        word.setId(entity.getId());
        List<String> formInfo = null;
        if (entity.getCategory().getFormsInfo() != null) {
            formInfo = Arrays.asList(entity.getCategory().getFormsInfo().split("; "));
        }
        word.setCategory(new Category(entity.getCategory().getId(),
                entity.getCategory().getName(),
                entity.getCategory().getDescription(),
                formInfo));
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
            smallForms.add(new FormWordViewModel(formWord.getContent(),
                                                 smallTranslation,
                                                 formWord.getTranscription(),
                                      formWord.getAudioData() != null));
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



    public List<PersonalWordInfoView> getPersonalModelsByCategory(Integer id, String userName) {
        List<PersonalWordInfoView> views = new ArrayList<>();
        for (Word word : getAllWordByCategory(id)) {
            long countQue = trainingRepository.getCountQuestionWithWordAndUser(word.getId(), userName);
            long rightQue = trainingRepository.getCountRightAnswersWithWordAndUser(word.getId(), userName);
            views.add(new PersonalWordInfoView(toWordViewModel(word), countQue, rightQue));
        }
        return views;
    }


    public List<PersonalWordInfoView> getAnonymousModelsByCategory(Integer id) {
        List<PersonalWordInfoView> views = new ArrayList<>();
        for (WordViewModel wordViewModel : getModelsByCategory(id)) {
            views.add(new PersonalWordInfoView(wordViewModel, 0, 0));
        }
        return views;
    }

    public void createWord(WrapperOfFormsOfWord formsOfWord, Integer categoryId) {
        WordEntity wordEntity = new WordEntity();
        List<FormWordEntity> formWordEntities = new ArrayList<>();
        int number = 1;
        for (FormOfWordForm form : formsOfWord.getList()) {
            byte[] audioData;
            try {
                System.out.println("Форма аудио: " + Arrays.toString(form.getAudioData().getBytes()));
                if (form.getAudioData().isEmpty() || form.getAudioData() == null) {
                    audioData = null;
                } else {
                    audioData = form.getAudioData().getBytes();
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
                audioData = null;
            }
            formWordEntities.add(new FormWordEntity(wordEntity,
                                                number++,
                                                form.getContent().strip(),
                                                form.getTranslation().strip(),
                                                form.getTranscription().strip(),
                                                audioData)
            );
        }
        CategoryEntity categoryEntity = categoryRepository.findById(categoryId).orElseThrow(
                () -> new EntityNotFoundException("Not found category with id = " + categoryId)
        );
        wordEntity.setCategory(categoryEntity);
        wordEntity.setForms(formWordEntities);
        wordRepository.save(wordEntity);
    }


    @Transactional
    public void delete(Integer wordId) {
        // сначала удалим все тренировки, в которых есть вопрос с этим словом
        trainingRepository.deleteIfHasWord(wordId);
        wordRepository.deleteById(wordId);
    }
}
