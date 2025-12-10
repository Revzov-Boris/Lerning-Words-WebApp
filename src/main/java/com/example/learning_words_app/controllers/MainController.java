package com.example.learning_words_app.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    @GetMapping
    public String helloPage(Authentication auth, Model model) {
        boolean isAuth = false;
        if (auth != null && auth.isAuthenticated()) {
            isAuth = true;
        }
        model.addAttribute("isAuth", isAuth);
        return "hello";
    }
}
