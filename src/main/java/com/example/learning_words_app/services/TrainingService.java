package com.example.learning_words_app.services;

import com.example.learning_words_app.Question;
import com.example.learning_words_app.Training;
import com.example.learning_words_app.Word;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TrainingService {
    long idTraining = 0;

    private List<Training> trainings = new ArrayList<>();

    @Autowired
    WordService wordService;

    public long createTraining(Integer categoryId, List<Integer> selectedIds) {
        Random random = new Random();
        List<Word> words = wordService.getAllWordByCategoryAndIds(categoryId, selectedIds);
        int countTypes = 3 + (words.get(0).getForms().size() - 1) * 4;
        List<Question> questions = new ArrayList<>();
        List<Word> shuffledWords = new ArrayList<>(words);
        Collections.shuffle(shuffledWords);
        for (Word word : shuffledWords) {
            questions.add(new Question(word, random.nextInt(1, countTypes + 1)));
        }
        trainings.add(new Training(++idTraining, questions, null, 0));
        System.out.println("Создана тренировка №" + idTraining);
        return idTraining;
    }

    public Optional<Training> getById(Long trainingId) {
        for (Training t : trainings) {
            if (t.getId().equals(trainingId)) {
                return Optional.ofNullable(t);
            }
        }
        return Optional.ofNullable(null);
    }


    public void addAnswers(Long trainingId, List<String> answers) {
        for (Training training : trainings) {
            if (training.getId().equals(trainingId) && training.getStatus() == 0) {
                training.setAnswers(answers);
                training.setStatus(1);
                break;
            }
        }
    }
}
