package com.example.learning_words_app.controllers;

import com.example.learning_words_app.services.UserService;
import com.example.learning_words_app.dto.RegistrationForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/auth")
public class AuthorizationController {
    @Autowired
    UserService userService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }


    @PostMapping("/login-error")
    public String onFailedLogin(
            @ModelAttribute("nickname") String username,
            RedirectAttributes redirectAttributes) {

        redirectAttributes.addFlashAttribute(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY, username);
        redirectAttributes.addFlashAttribute("badLoginForm", true);
        return "redirect:/auth/login";
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
