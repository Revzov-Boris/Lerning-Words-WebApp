package com.example.learning_words_app.utils.validation;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = UniqueLanguageTitleValidator.class)
public @interface UniqueLanguageTitle {
    String message() default "Язык с таким названием уже есть!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
