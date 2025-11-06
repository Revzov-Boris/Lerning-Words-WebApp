package com.example.learning_words_app.controllers;

import com.example.learning_words_app.FormWord;
import com.example.learning_words_app.Question;
import com.example.learning_words_app.Word;
import com.example.learning_words_app.entities.CategoryEntity;
import com.example.learning_words_app.entities.QuestionEntity;
import com.example.learning_words_app.entities.TrainingEntity;
import com.example.learning_words_app.services.CategoryService;
import com.example.learning_words_app.services.TrainingService;
import com.example.learning_words_app.services.WordService;
import com.example.learning_words_app.viewmodels.FormWordViewModel;
import com.example.learning_words_app.viewmodels.ResultQuestionViewModel;
import com.example.learning_words_app.viewmodels.TrainingResultViewModel;
import com.example.learning_words_app.viewmodels.WordViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
                               @RequestParam(required = false) List<Integer> selectedIds,
                               RedirectAttributes redirectAttributes) {
        System.out.println("Выбранные слова: " + selectedIds);
        if (selectedIds == null) {
            System.out.println("0 cлов выбрано, переправляю");
            redirectAttributes.addFlashAttribute("zeroWordsText", "Выберете хотя бы несколько слов!");
            return String.format("redirect:/categories/%d/start-training", id);
        }
        return String.format("redirect:/categories/%d/training/%d", id, trainingService.createTraining(id, selectedIds));
    }


    @GetMapping("/{categoryId}/training/{trainingId}")
    public String training(Model model, @PathVariable Integer categoryId, @PathVariable Long trainingId) {
        TrainingEntity training = trainingService.getById(trainingId).orElseThrow();
        CategoryEntity category = categoryService.getById(categoryId).orElseThrow();
        model.addAttribute("trainingId", trainingId);
        model.addAttribute("category", category);
        List<Question> questions = new ArrayList<>();
        for (QuestionEntity entity : training.getQuestions()) {
            Word word = wordService.getByCategoryIdAndId(entity.getCategory().getId(), entity.getWordId()).orElseThrow();
            questions.add(new Question(word, entity.getType()));
        }
        model.addAttribute("questions", questions);
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
        TrainingResultViewModel result = trainingService.makeResult(trainingId);

        model.addAttribute("resultTraining", result);
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
