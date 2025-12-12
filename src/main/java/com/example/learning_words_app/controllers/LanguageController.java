package com.example.learning_words_app.controllers;

import com.example.learning_words_app.dto.LanguageAddForm;
import com.example.learning_words_app.dto.LanguageViewModel;
import com.example.learning_words_app.services.LanguageService;
import com.example.learning_words_app.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;

@Controller
@RequestMapping("/languages")
public class LanguageController {
    @Autowired
    LanguageService languageService;
    @Autowired
    UserService userService;


    // добавляет пустую модель во все запросы, чтобы get-запросы проходили нормально
    @ModelAttribute("langForm")
    public LanguageAddForm initForm() {
        return new LanguageAddForm("");
    }


    @GetMapping
    public String allLanguages(Model model, Authentication auth) {
        List<LanguageViewModel> languages = languageService.getAll();
        boolean isAdmin = false;
        if (auth != null && auth.isAuthenticated()) {
            isAdmin = userService.isAdmin(auth.getName());
        }
        model.addAttribute("isAdmin", isAdmin);
        model.addAttribute("languages", languages);
        return "languages";
    }


    @PostMapping("/admin/delete/{languageId}")
    public String deleteLanguage(@PathVariable Integer languageId) {
        System.out.println("Удалили язык с id = " + languageId);
        languageService.deleteLanguageById(languageId);
        return "redirect:/languages";
    }


    @GetMapping("/admin/add")
    public String addLanguagePage() {
        return "addLanguage";
    }


    @PostMapping("/admin/add")
    public String addLanguage(@Valid LanguageAddForm form,
                              BindingResult bindingResult,
                              RedirectAttributes redirectAttributes) {
        System.out.println("Дошёёёл");
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("langForm", form);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.langForm", bindingResult);
            return "redirect:/languages/admin/add";
        }
        languageService.createLanguage(form);
        return "redirect:/languages";
    }
}
