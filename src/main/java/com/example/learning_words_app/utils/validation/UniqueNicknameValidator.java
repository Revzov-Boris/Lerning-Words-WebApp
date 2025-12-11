package com.example.learning_words_app.utils.validation;

import com.example.learning_words_app.repositories.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UniqueNicknameValidator implements ConstraintValidator<UniqueNickname, String> {
    private final UserRepository userRepository;

    public UniqueNicknameValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isBlank()) {
            return true;
        }
        return userRepository.findByNickname(value).isEmpty();
    }
}
