package com.example.learning_words_app.services;

import com.example.learning_words_app.FormWord;
import com.example.learning_words_app.Word;
import com.example.learning_words_app.entities.ThreeFormWordEntity;
import com.example.learning_words_app.entities.TwoFormWordEntity;
import com.example.learning_words_app.entities.WordEntity;
import com.example.learning_words_app.repositories.ThreeFormWordRepository;
import com.example.learning_words_app.repositories.TwoFormWordRepository;
import com.example.learning_words_app.repositories.WordRepository;
import com.example.learning_words_app.viewmodels.FormWordViewModel;
import com.example.learning_words_app.viewmodels.WordViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class WordService {
    @Autowired
    WordRepository wordRepository;
    @Autowired
    TwoFormWordRepository twoFormWordRepository;
    @Autowired
    ThreeFormWordRepository threeFormWordRepository;

    public Optional<Word> getByCategoryIdAndId(Integer categoryId, Integer id) {
        List<Word> words = getAllWordByCategory(categoryId);
        Word word = null;
        for (Word w : words) {
            if (w.getId().equals(id)) {
                word = w;
                break;
            }
        }
        if (word == null) {
            return Optional.ofNullable(null);
        }
        return Optional.ofNullable(word);
    }

    public List<Word> getAllWordByCategory(int id) {
        List<WordEntity> oneFormWords = wordRepository.findByCategoryId(id);
        List<TwoFormWordEntity> twoFormWords = twoFormWordRepository.findByCategoryId(id);
        List<ThreeFormWordEntity> threeFormWords = threeFormWordRepository.findByCategoryId(id);

        List<Word> wordsList = new ArrayList<>();
        if (!oneFormWords.isEmpty()) {
            for (WordEntity wordEntity : oneFormWords) {
                wordsList.add(wordEntity.toWord());
            }
        } else if (!twoFormWords.isEmpty()) {
            for (TwoFormWordEntity wordEntity : twoFormWords) {
                wordsList.add(wordEntity.toWord());
            }
        } else if (!threeFormWords.isEmpty()){
            for (ThreeFormWordEntity wordEntity : threeFormWords) {
                wordsList.add(wordEntity.toWord());
            }
        }
        return wordsList;
    }

    public List<Word> getAllWordByCategoryAndIds(Integer categoryId, List<Integer> selectedIds) {
        List<Word> allByCategory = getAllWordByCategory(categoryId);
        return allByCategory.stream().filter(w -> selectedIds.contains(w.getId())).toList();
    }
}
