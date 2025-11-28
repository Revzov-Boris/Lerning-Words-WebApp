package com.example.learning_words_app.services;

import com.example.learning_words_app.Question;
import com.example.learning_words_app.Word;
import com.example.learning_words_app.entities.QuestionEntity;
import com.example.learning_words_app.entities.TrainingEntity;
import com.example.learning_words_app.entities.WordEntity;
import com.example.learning_words_app.repositories.QuestionRepository;
import com.example.learning_words_app.repositories.TrainingRepository;
import com.example.learning_words_app.viewmodels.QuestionViewModel;
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
        List<Word> words = wordService.getAllWordByIds(selectedIds);
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
            WordEntity wordEntity = wordService.getEntityById(word.getId());
            questionEntity.setWord(wordEntity);
            questionEntity.setTraining(trainingEntity);
            questionEntity.setType(generateType(word));
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


    public String getTokenByTrainingId(Long id) {
        TrainingEntity trainingEntity = getById(id);
        return trainingEntity.getToken();
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


    public List<QuestionViewModel> getQuestionsByTraining(Long trainingId) {
        return getById(trainingId).getQuestions().stream().map(
                e -> toQuestionView(e)
        ).toList();
    }


    public TrainingResultViewModel makeResult(Long trainingId) {
        TrainingEntity trainingEntity = getById(trainingId);
        if (trainingEntity.getStatus() != 1) {
            throw new IllegalStateException("Try to get result of a not completed training");
        }
        List<ResultQuestionViewModel> resultsQuestions = new ArrayList<>();
        for (QuestionEntity questionEntity : trainingEntity.getQuestions()) {
            Word word = WordService.toWord(questionEntity.getWord());
            Question question = new Question(word, questionEntity.getType());
            String userAnswer = questionEntity.getAnswer();
            boolean isRight;
            if (Question.isRusAnswer(question.getType())) {
                System.out.println(Arrays.asList(question.goodAnswer().split(" ")).contains(userAnswer));
                isRight = Arrays.asList(question.goodAnswer().split(" ")).contains(userAnswer);
            } else {
                System.out.println(question.goodAnswer().equals(userAnswer));
                isRight = question.goodAnswer().equals(userAnswer);
            }
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


    private QuestionViewModel toQuestionView(QuestionEntity questionEntity) {
        Question question = new Question(WordService.toWord(questionEntity.getWord()), questionEntity.getType());
        return new QuestionViewModel(
                WordService.toWordViewModel(WordService.toWord(questionEntity.getWord())),
                questionEntity.getType(),
                question.toText()
        );
    }


    private static int generateType(Word word) {
        Random random = new Random();
        int countTypes = 3 + (word.getForms().size() - 1) * 4; // зависит от количества форм в словах
        int type;
        while (true) {
            type = random.nextInt(1, countTypes + 1);
            // если вопрос с аудио, а этого аудио нет в БД, то генерируем другой тип
            if (Question.isAudioType(type) && word.getForms().get(Question.formIndexByType(type)).getAudioData() == null) {
                continue;
            }
            break;
        }
        return type;
    }
}
