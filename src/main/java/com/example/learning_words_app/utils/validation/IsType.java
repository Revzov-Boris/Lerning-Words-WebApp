package com.example.learning_words_app.utils.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = IsTypeValidator.class)
public @interface IsType {
    String message() default "Некорректный размер файла!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String[] typeNames() default {"audio/mp3"};
}
