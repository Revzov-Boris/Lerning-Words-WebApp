package com.example.learning_words_app.controllers;

import com.example.learning_words_app.dto.*;
import com.example.learning_words_app.services.CategoryService;
import com.example.learning_words_app.services.TrainingService;
import com.example.learning_words_app.services.UserService;
import com.example.learning_words_app.services.WordService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
@RequestMapping("/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private WordService wordService;
    @Autowired
    private TrainingService trainingService;
    @Autowired
    private UserService userService;


    // добавляет пустую модель во все запросы, чтобы get-запросы проходили нормально
    @ModelAttribute("catForm")
    public CategoryAddForm initForm() {
        return new CategoryAddForm("", "", 0, "");
    }


    // добавляет пустую модель во все запросы, чтобы get-запросы проходили нормально
    @ModelAttribute("formsOfWord")
    public WrapperOfFormsOfWord initWrapper() {
        return new WrapperOfFormsOfWord();
    }


    @GetMapping
    public String listCategories(Model model, @RequestParam Integer language, Authentication auth) {
        List<CategoryViewModel> allCat = categoryService.allCategoryByLanguage(language);
        model.addAttribute("categories", allCat);
        boolean isAdmin = false;
        if (auth != null && auth.isAuthenticated()) {
            String userName = auth.getName();
            isAdmin = userService.isAdmin(userName);
        }
        model.addAttribute("isAdmin", isAdmin);
        model.addAttribute("languageId", language);
        return "categories";
    }


    @GetMapping("/{id}")
    public String wordsOfCategory(@PathVariable Integer id, Model model, Authentication auth) {
        CategoryViewModel category = categoryService.getById(id);
        List<PersonalWordInfoView> views;
        boolean isAuth = false;
        boolean isAdmin = false;
        if (auth != null && auth.isAuthenticated()) {
            String userName = auth.getName();
            isAuth = true;
            isAdmin = userService.isAdmin(userName);
            views = wordService.getPersonalModelsByCategory(id, userName);
        } else {
            views = wordService.getAnonymousModelsByCategory(id);
        }
        model.addAttribute("category", category);
        model.addAttribute("views", views);
        model.addAttribute("isAuth", isAuth);
        model.addAttribute("isAdmin", isAdmin);
        return "words-table";
    }


    @GetMapping("/{id}/admin/add")
    public String addWordPage(@PathVariable Integer id, Model model) {
        CategoryViewModel categoryViewModel = categoryService.getById(id);
        WrapperOfFormsOfWord formsOfWord = new WrapperOfFormsOfWord();
        System.out.println(categoryViewModel);
        model.addAttribute("formsOfWord", formsOfWord);
        model.addAttribute("category", categoryViewModel);
        return "addWord";
    }


    @PostMapping("/{id}/admin/add")
    public String addWordPage(@PathVariable Integer id,
                              @ModelAttribute("formsOfWord") WrapperOfFormsOfWord formsOfWord,
                              BindingResult bindingResult,
                              RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("formsOfWord", formsOfWord);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.formsOfWord", bindingResult);
            System.out.println("Валидация");
            return "redirect:/categories/" + id + "/admin/add";
        }
        System.out.println("Дошёллл: " + formsOfWord.getList());
        wordService.createWord(formsOfWord, id);
        return "redirect:/categories/" + id;
    }


    @PostMapping("/{id}/admin/delete/{wordId}")
    public String deleteWord(@PathVariable Integer id, @PathVariable Integer wordId,
                             RedirectAttributes redirectAttributes) {
        wordService.delete(wordId);
        redirectAttributes.addFlashAttribute("wasDel", "Удалено слово");
        return "redirect:/categories/" + id;
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
                               HttpServletResponse response,
                               Authentication auth) {
        String nickname = auth.getName();
        System.out.println("Тренировку создал: " + auth.getName());
        System.out.println("Выбранные слова: " + selectedIds);
        if (selectedIds == null) {
            System.out.println("0 cлов выбрано, переправляю");
            redirectAttributes.addFlashAttribute("zeroWordsText", "Выберете хотя бы несколько слов!");
            return String.format("redirect:/categories/%d/start-training", id);
        }
        Long newTrainingId = trainingService.createTraining(id, nickname, selectedIds);
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


    @PostMapping("/admin/delete/{categoryId}")
    public String deleteLanguage(@PathVariable Integer categoryId) {
        System.out.println("Удалили язык с id = " + categoryId);
        categoryService.deleteCategoryById(categoryId);
        return "redirect:/languages";
    }


    @GetMapping("/admin/add/{languageId}")
    public String addLanguagePage(@PathVariable int languageId, Model model) {
        model.addAttribute("languageId", languageId);
        return "addCategory";
    }


    @PostMapping("/admin/add")
    public String addLanguage(@RequestParam Integer languageId,
                              @Valid CategoryAddForm form,
                              BindingResult bindingResult,
                              RedirectAttributes redirectAttributes) {
        System.out.println("Дошёёёл");
        if (bindingResult.hasErrors()
                || !categoryService.isUniqueInLanguage(form.name(), languageId)
                || !CategoryService.isValidFormsInfo(form.formsInfo(), form.countForms())) {
            System.out.println(CategoryService.getErrorCodeFormsInfo(form.formsInfo(), form.countForms()));
            String errorCodeForms = CategoryService.getErrorCodeFormsInfo(form.formsInfo(), form.countForms());
            if (!CategoryService.isValidFormsInfo(form.formsInfo(), form.countForms())) {
                bindingResult.rejectValue(
               "formsInfo",
                    errorCodeForms,
                    CategoryService.getErrorMessageFormsInfo(errorCodeForms)
                );
            }
            if (!categoryService.isUniqueInLanguage(form.name(), languageId)) {
                System.out.println("Сервисная валидация");
                bindingResult.rejectValue(
                    "name",
                "notUnique",
            "категория " + form.name() + " уже есть в этом языке!"
                );
            }
            redirectAttributes.addFlashAttribute("catForm", form);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.catForm", bindingResult);
            System.out.println("Валидация");
            System.out.println(form);
            return "redirect:/categories/admin/add/" + languageId;
        }
        System.out.println("Добавляю");
        categoryService.createCategory(form, languageId);
        return "redirect:/languages";
    }
}
