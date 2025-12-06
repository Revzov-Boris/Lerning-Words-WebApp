package com.example.learning_words_app.controllers;

import com.example.learning_words_app.services.UserService;
import com.example.learning_words_app.dto.ProfileInfoViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/profile")
    public String userProfile(Model model, Authentication authentication) {
        ProfileInfoViewModel info = userService.getInfoByName(authentication.getName());
        model.addAttribute("personalInfo", info);
        return "userProfile";
    }

}
