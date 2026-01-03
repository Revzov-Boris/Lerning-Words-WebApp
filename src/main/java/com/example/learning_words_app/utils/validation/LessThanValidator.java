package com.example.learning_words_app.utils.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;


public class LessThanValidator implements ConstraintValidator<LessThan, MultipartFile> {
    private long maxSize;


    @Override
    public void initialize(LessThan lessThan) {
        this.maxSize = lessThan.maxSize();
    }


    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
        if (file.isEmpty() || file == null) {
            return true;
        }

        if (file.getSize() > maxSize) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                    "Размер файла равен " + file.getSize() / 1_048_576 + " МБ " + "(максимально допустимо: " +  (maxSize / 1_048_576) + " МБ)"
            ).addConstraintViolation();
            return false;
        } else {
            return true;
        }
    }
}
