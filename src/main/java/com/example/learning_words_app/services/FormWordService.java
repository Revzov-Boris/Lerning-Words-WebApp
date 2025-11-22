package com.example.learning_words_app.services;

import com.example.learning_words_app.entities.FormWordEntity;
import com.example.learning_words_app.repositories.FormWordRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FormWordService {
    @Autowired
    FormWordRepository formWordRepository;

    public byte[] getAudioDataByWordAndNumber(int wordId, int number) {
        byte[] audioData = formWordRepository.findAudioDataByWordIdAndNumber(wordId, number + 1).orElseThrow(
                () -> new EntityNotFoundException("Not found form of word with word_id = " + wordId + " and " +
                        "number = " + number)
        );
        return audioData;
    }


    @Transactional
    public void addAudio(int id, byte[] audioData) {
        FormWordEntity entity = formWordRepository.getById(id);
        entity.setAudioData(audioData);
        formWordRepository.save(entity);
    }
}
