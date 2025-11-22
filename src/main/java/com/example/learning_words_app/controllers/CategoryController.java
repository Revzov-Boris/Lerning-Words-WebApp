package com.example.learning_words_app.controllers;

import com.example.learning_words_app.services.CategoryService;
import com.example.learning_words_app.services.TrainingService;
import com.example.learning_words_app.services.WordService;
import com.example.learning_words_app.viewmodels.CategoryViewModel;
import com.example.learning_words_app.viewmodels.QuestionViewModel;
import com.example.learning_words_app.viewmodels.TrainingResultViewModel;
import com.example.learning_words_app.viewmodels.WordViewModel;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
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
        List<CategoryViewModel> allCat = categoryService.allCategory();
        model.addAttribute("categories", allCat);
        return "categories";
    }


    @GetMapping("/{id}")
    public String wordsOfCategory(@PathVariable Integer id, Model model) {
        CategoryViewModel category = categoryService.getById(id);
        List<WordViewModel> views = wordService.getModelsByCategory(id);
        model.addAttribute("category", category);
        model.addAttribute("words", views);
        return "words-table";
    }


    @GetMapping("/{id}/start-training")
    public String createTrainingForm(@PathVariable Integer id, Model model) {
        List<WordViewModel> words = wordService.getModelsByCategory(id);
        CategoryViewModel category = categoryService.getById(id);
        model.addAttribute("category", category);
        model.addAttribute("words", words);
        return "form-training";
    }


    @PostMapping("/{id}/start-training")
    public String makeTraining(@PathVariable Integer id,
                               @RequestParam(required = false) List<Integer> selectedIds,
                               RedirectAttributes redirectAttributes,
                               HttpServletResponse response) {
        System.out.println("Выбранные слова: " + selectedIds);
        if (selectedIds == null) {
            System.out.println("0 cлов выбрано, переправляю");
            redirectAttributes.addFlashAttribute("zeroWordsText", "Выберете хотя бы несколько слов!");
            return String.format("redirect:/categories/%d/start-training", id);
        }
        Long newTrainingId = trainingService.createTraining(id, selectedIds);
        Cookie cookie = new Cookie("token", trainingService.getTokenByTrainingId(newTrainingId));
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(-1);
        response.addCookie(cookie);
        return String.format("redirect:/categories/%d/training/%d", id, newTrainingId);
    }


    @GetMapping("/{categoryId}/training/{trainingId}")
    public String training(Model model, @PathVariable Integer categoryId, @PathVariable Long trainingId) {
        CategoryViewModel category = categoryService.getById(categoryId);
        List<QuestionViewModel> questions = trainingService.getQuestionsByTraining(trainingId);
        model.addAttribute("trainingId", trainingId);
        model.addAttribute("category", category);
        model.addAttribute("questions", questions);
        return "training";
    }


    @PostMapping("/{categoryId}/training/{trainingId}")
    public String sendResult(@RequestParam List<String> answers, @PathVariable Integer categoryId,
                             @PathVariable Long trainingId, HttpServletRequest request) {
        // добавляем ответы
        System.out.println("Получены ответы: " + answers);
        String token = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    token = cookie.getValue();
                    break;
                }
            }
        }
        if (token == null) {
            throw new SecurityException("Attempt to send answers without cookies");
        }
        trainingService.addAnswers(trainingId, answers, token);
        return String.format("redirect:/categories/%d/training/%d/result", categoryId, trainingId);
    }


    @GetMapping("/{categoryId}/training/{trainingId}/result")
    public String showResults(@PathVariable Long trainingId, @PathVariable Integer categoryId, Model model) {
        TrainingResultViewModel result = trainingService.makeResult(trainingId);
        model.addAttribute("resultTraining", result);
        return "result";
    }
}
