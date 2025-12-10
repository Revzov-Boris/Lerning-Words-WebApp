package com.example.learning_words_app.controllers;

import com.example.learning_words_app.services.UserService;
import com.example.learning_words_app.dto.RegistrationForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/auth")
public class AuthorizationController {
    @Autowired
    UserService userService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }


    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }


    @PostMapping("/registration")
    public String registration(@ModelAttribute RegistrationForm form) {
        System.out.println("Пытается зарегистрироваться: " + form.nickname());
        userService.createAccount(form);
        return "redirect:/auth/login";
    }
}
