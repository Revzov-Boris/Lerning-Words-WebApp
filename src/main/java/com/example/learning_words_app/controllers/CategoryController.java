package com.example.learning_words_app.controllers;

import com.example.learning_words_app.FormWord;
import com.example.learning_words_app.Question;
import com.example.learning_words_app.Training;
import com.example.learning_words_app.Word;
import com.example.learning_words_app.entities.CategoryEntity;
import com.example.learning_words_app.services.CategoryService;
import com.example.learning_words_app.services.TrainingService;
import com.example.learning_words_app.services.WordService;
import com.example.learning_words_app.viewmodels.FormWordViewModel;
import com.example.learning_words_app.viewmodels.Result;
import com.example.learning_words_app.viewmodels.WordViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private WordService wordService;
    @Autowired
    private TrainingService trainingService;

    @GetMapping
    public String listCategories(Model model) {
        List<CategoryEntity> allCat = categoryService.allCategory();
        model.addAttribute("categories", allCat);
        return "categories";
    }


    @GetMapping("/{id}")
    public String wordsOfCategory(@PathVariable Integer id, Model model) {
        CategoryEntity category = categoryService.getById(id).orElseThrow();
        List<Word> words = wordService.getAllWordByCategory(id);
        List<WordViewModel> views = new ArrayList<>();
        for (Word word : words) {
            views.add(toWordViewModel(word));
        }
        model.addAttribute("category", category);
        model.addAttribute("words", views);
        return "words-table";
    }


    @GetMapping("/{id}/start-training")
    public String createTrainingForm(@PathVariable Integer id, Model model) {
        List<Word> words = wordService.getAllWordByCategory(id);
        CategoryEntity category = categoryService.getById(id).orElseThrow();
        List<WordViewModel> views = new ArrayList<>();
        for (Word word : words) {
            views.add(toWordViewModel(word));
        }
        model.addAttribute("category", category);
        model.addAttribute("words", views);
        return "form-training";
    }


    @PostMapping("/{id}/start-training")
    public String makeTraining(@PathVariable Integer id,
                               @RequestParam List<Integer> selectedIds) {
        return String.format("redirect:/categories/%d/training/%d", id, trainingService.createTraining(id, selectedIds));
    }


    @GetMapping("/{categoryId}/training/{trainingId}")
    public String training(Model model, @PathVariable Integer categoryId, @PathVariable Long trainingId) {
        Training training = trainingService.getById(trainingId).orElseThrow();
        CategoryEntity category = categoryService.getById(categoryId).orElseThrow();
        model.addAttribute("trainingId", trainingId);
        model.addAttribute("category", category);
        model.addAttribute("questions", training.getQuestions());
        return "training";
    }


    @PostMapping("/{categoryId}/training/{trainingId}")
    public String sendResult(@RequestParam List<String> answers, @PathVariable Integer categoryId, @PathVariable Long trainingId) {
        // добавляем ответы
        System.out.println("Ответы добавлены: " + answers);
        trainingService.addAnswers(trainingId, answers);
        return String.format("redirect:/categories/%d/training/%d/result", categoryId, trainingId);
    }


    @GetMapping("/{categoryId}/training/{trainingId}/result")
    public String showResults(@PathVariable Long trainingId, @PathVariable Integer categoryId, Model model) {
        Training training = trainingService.getById(trainingId).orElseThrow();
        int goodAns = 0;
        List<List<String>> ansAndQue = new ArrayList<>();
        for (int i = 0; i < training.getQuestions().size(); i++) {
            String answer = training.getAnswers().get(i);
            Question question = training.getQuestions().get(i);
            ansAndQue.add(List.of(answer, question.goodAnswer()));
            if (answer.equals(question.goodAnswer())) {
                goodAns++;
            }
        }
        System.out.println("Верные ответы: " + ansAndQue);
        Result result = new Result(goodAns, ansAndQue);
        model.addAttribute("result", result);
        return "result";
    }

    private WordViewModel toWordViewModel(Word word) {
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
        return new WordViewModel(word.getId(), word.getCategoryId(), smallForms);
    }
}
