package com.example.learning_words_app.controllers;

import com.example.learning_words_app.FormWord;
import com.example.learning_words_app.Word;
import com.example.learning_words_app.entities.CategoryEntity;
import com.example.learning_words_app.entities.TwoFormWordEntity;
import com.example.learning_words_app.entities.WordEntity;
import com.example.learning_words_app.repositories.TwoFormWordRepository;
import com.example.learning_words_app.repositories.WordRepository;
import com.example.learning_words_app.services.CategoryService;
import com.example.learning_words_app.services.WordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/categories")
public class MainController {
    @Autowired
    private WordRepository wordRepository;
    @Autowired
    private TwoFormWordRepository twoFormWordRepository;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private WordService wordService;

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
        model.addAttribute("category", category);
        model.addAttribute("words", words);
        return "words-table";
    }
}
