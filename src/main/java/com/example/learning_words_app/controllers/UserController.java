package com.example.learning_words_app.controllers;

import com.example.learning_words_app.dto.TrainingViewModel;
import com.example.learning_words_app.services.TrainingService;
import com.example.learning_words_app.services.UserService;
import com.example.learning_words_app.dto.ProfileInfoViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private TrainingService trainingService;

    @GetMapping("/profile")
    public String userProfile(Model model, Authentication authentication) {
        ProfileInfoViewModel info = userService.getInfoByName(authentication.getName());
        model.addAttribute("personalInfo", info);
        return "userProfile";
    }


    @GetMapping("/profile/trainings")
    public String trainingsListPage(Model model, Authentication authentication) {
        List<TrainingViewModel> trainings = trainingService.getTrainingsByNick(authentication.getName());
        model.addAttribute("trainings", trainings);
        return "userTrainings";
    }
}
