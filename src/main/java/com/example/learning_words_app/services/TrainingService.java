package com.example.learning_words_app.services;

import com.example.learning_words_app.Question;
import com.example.learning_words_app.Word;
import com.example.learning_words_app.entities.CategoryEntity;
import com.example.learning_words_app.entities.QuestionEntity;
import com.example.learning_words_app.entities.TrainingEntity;
import com.example.learning_words_app.repositories.QuestionRepository;
import com.example.learning_words_app.repositories.TrainingRepository;
import com.example.learning_words_app.viewmodels.ResultQuestionViewModel;
import com.example.learning_words_app.viewmodels.TrainingResultViewModel;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class TrainingService {
    @Autowired
    private TrainingRepository trainingRepository;
    @Autowired
    private WordService wordService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private QuestionRepository questionRepository;


    public long createTraining(Integer categoryId, List<Integer> selectedIds) {
        Random random = new Random();
        List<Word> words = wordService.getAllWordByCategoryAndIds(categoryId, selectedIds);
        int countTypes = 3 + (words.getFirst().getForms().size() - 1) * 4; // зависит от количества форм в словах
        List<QuestionEntity> questions = new ArrayList<>();
        List<Word> shuffledWords = new ArrayList<>(words);
        Collections.shuffle(shuffledWords);
        // создаем и сохраняем тренировку
        TrainingEntity trainingEntity = new TrainingEntity();
        trainingEntity.setStatus(0);
        trainingEntity.setCreateDate(LocalDateTime.now());
        trainingEntity.setToken(UUID.randomUUID().toString());
        trainingEntity = trainingRepository.save(trainingEntity);
        // создаём вопросы, привязываем их к тренировке
        int index = 0;
        for (Word word : shuffledWords) {
            QuestionEntity questionEntity = new QuestionEntity();
            CategoryEntity category = categoryService.getById(categoryId);
            questionEntity.setCategory(category);
            questionEntity.setTraining(trainingEntity);
            questionEntity.setWordId(word.getId());
            questionEntity.setType(random.nextInt(1, countTypes + 1));
            questionEntity.setIndexInTraining(index++);
            questions.add(questionEntity);
        }
        // привязываем слова к тренировке
        trainingEntity.setQuestions(questions);
        // пересохраняем тренировку
        TrainingEntity createdTraining = trainingRepository.save(trainingEntity);
        System.out.println("Создана тренировка №" + createdTraining.getId());
        return createdTraining.getId();
    }

    public TrainingEntity getById(Long trainingId) {
        TrainingEntity trainingEntity = trainingRepository.findById(trainingId).orElseThrow(
                () -> new EntityNotFoundException("Not found training with id = " + trainingId)
        );
        return trainingEntity;
    }


    public void addAnswers(Long trainingId, List<String> answers, String token) {
        TrainingEntity training = trainingRepository.getById(trainingId);
        System.out.println("token training: " + training.getToken());
        System.out.println("user token: " + token);
        if (!training.getToken().equals(token)) {
            throw new SecurityException("Attempt send answers to aline training");
        }
        if (training.getStatus() == 0 && training.getQuestions().size() == answers.size()) {
            for (int i = 0; i < training.getQuestions().size(); i++) {
                training.getQuestions().get(i).setAnswer(answers.get(i));
            }
            training.setStatus(1);
            training.setEndDate(LocalDateTime.now());
            questionRepository.saveAll(training.getQuestions());
        } else  {
            if (training.getStatus() == 1) {
                throw new IllegalStateException("Can not insert answers into a completed training");
            }
            if (training.getQuestions().size() != answers.size()) {
                throw new IllegalStateException("Try to insert " + answers.size() +
                        " answers into a training with " + training.getQuestions().size() + " questions");
            }
        }
    }

    public TrainingResultViewModel makeResult(Long trainingId) {
        TrainingEntity trainingEntity = getById(trainingId);
        if (trainingEntity.getStatus() != 1) {
            throw new IllegalStateException("Try to get result of a not completed training");
        }
        List<ResultQuestionViewModel> resultsQuestions = new ArrayList<>();
        for (QuestionEntity questionEntity : trainingEntity.getQuestions()) {
            Word word = wordService.getByCategoryIdAndId(questionEntity.getCategory().getId(), questionEntity.getWordId());
            Question question = new Question(word, questionEntity.getType());
            String userAnswer = questionEntity.getAnswer();
            Set<Integer> typesWithAnswerOnRus = Set.of(1, 5, 9);
            boolean isRight;
            System.out.println("Правильный ответ: '" + question.goodAnswer() + "'");
            System.out.println("Пользователя ответ: '" + userAnswer + "'");
            System.out.println("Коды  ожидаемого: " + Arrays.toString(question.goodAnswer().getBytes()));
            System.out.println("Коды полученного: " + Arrays.toString(userAnswer.getBytes()));
            System.out.println("Ожидаемый: " + stringToHex(question.goodAnswer()));
            System.out.println("Полученный: " + stringToHex(userAnswer));
            if (typesWithAnswerOnRus.contains(question.getType())) {
                System.out.println(Arrays.asList(question.goodAnswer().split(" ")).contains(userAnswer));
                isRight = Arrays.asList(question.goodAnswer().split(" ")).contains(userAnswer);
            } else {
                System.out.println(question.goodAnswer().equals(userAnswer));
                isRight = question.goodAnswer().equals(userAnswer);
            }

            System.out.println("ответ: " + isRight);
            resultsQuestions.add(new ResultQuestionViewModel(question, userAnswer, isRight));
        }
        String time;
        Duration duration = Duration.between(trainingEntity.getCreateDate(), trainingEntity.getEndDate());
        if (duration.toHours() >= 1) {
            time = String.format("%d часов %d м %d с", duration.toHours(), duration.toMinutesPart(), duration.toSecondsPart());
        } else {
            time = String.format("%d м %d с", duration.toMinutesPart(), duration.toSecondsPart());
        }
        int countRightAnswer = 0;
        for (ResultQuestionViewModel resQue : resultsQuestions) {
            if (resQue.isRight()) countRightAnswer++;
        }

        return new TrainingResultViewModel(time, countRightAnswer, resultsQuestions);
    }

    // Вспомогательный метод
    private static String stringToHex(String str) {
        StringBuilder hex = new StringBuilder();
        for (char c : str.toCharArray()) {
            hex.append(String.format("\\u%04x ", (int) c));
        }
        return hex.toString();
    }
}
