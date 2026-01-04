package com.example.learning_words_app.utils.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;

public class IsTypeValidator implements ConstraintValidator<IsType, MultipartFile> {
    private String[] typeNames;

    @Override
    public void initialize(IsType isType) {
        this.typeNames = isType.typeNames();
    }

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
        if (file == null || file.isEmpty()) {
            return true;
        }

        for (String type : typeNames) {
            if (file.getContentType().equals(type)) {
                return true;
            }
        }
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(
                "Ваш файл: " + file.getContentType() + ", нужно: " + Arrays.toString(typeNames)
        ).addConstraintViolation();
        return false;
    }
}
