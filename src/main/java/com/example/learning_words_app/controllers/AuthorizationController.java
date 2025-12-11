package com.example.learning_words_app.controllers;

import com.example.learning_words_app.services.UserService;
import com.example.learning_words_app.dto.RegistrationForm;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/auth")
public class AuthorizationController {
    @Autowired
    UserService userService;

    // добавляет пустую модель во все запросы, чтобы get-запросы проходили нормально
    @ModelAttribute("regForm")
    public RegistrationForm initForm() {
        return new RegistrationForm("", "");
    }


    @GetMapping("/login")
    public String login() {
        return "login";
    }


    @PostMapping("/login-error")
    public String onFailedLogin(
            @ModelAttribute RegistrationForm registrationForm,
            RedirectAttributes redirectAttributes) {

        redirectAttributes.addFlashAttribute("nickname", registrationForm.nickname());
        redirectAttributes.addFlashAttribute("badLoginForm", true);
        return "redirect:/auth/login";
    }


    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }


    @PostMapping("/registration")
    public String registration(@Valid RegistrationForm form,
                               BindingResult bindingResult,
                               RedirectAttributes redirectAttributes) {
        System.out.println("Пытается зарегистрироваться: " + form.nickname());
        if (bindingResult.hasErrors()) {
            System.out.println("Не удалось зарегистрироваться");
            redirectAttributes.addFlashAttribute("regForm", form);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.regForm", bindingResult);
            return "redirect:/auth/registration";
        }
        userService.createAccount(form);
        return "redirect:/auth/login";
    }
}
