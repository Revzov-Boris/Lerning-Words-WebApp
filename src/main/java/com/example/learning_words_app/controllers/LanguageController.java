package com.example.learning_words_app.controllers;

import com.example.learning_words_app.dto.LanguageViewModel;
import com.example.learning_words_app.services.LanguageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;

@Controller
@RequestMapping("/languages")
public class LanguageController {
    @Autowired
    LanguageService languageService;

    @GetMapping
    public String allLanguages(Model model) {
        List<LanguageViewModel> languages = languageService.getAll();
        model.addAttribute("languages", languages);
        return "languages";
    }
}
