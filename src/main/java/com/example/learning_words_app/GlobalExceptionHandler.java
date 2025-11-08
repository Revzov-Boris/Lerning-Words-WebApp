package com.example.learning_words_app;

import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {
    private final static Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);


    @ExceptionHandler(Exception.class)
    public String handlerSomeException(Exception e, Model model) {
        log.error("Exception: " + e.getMessage());
        model.addAttribute("info", "Неопределенная ошибка");
        model.addAttribute("message", e.getMessage());
        return "error";
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public String handlerSomeException(EntityNotFoundException e, Model model) {
        log.error("EntityNotFoundException: " + e.getMessage());
        model.addAttribute("info", "Вы пытаетесь найти несуществующую страницу");
        model.addAttribute("message", e.getMessage());
        return "error";
    }

    @ExceptionHandler(IndexOutOfBoundsException.class)
    public String handlerIndexOut(IndexOutOfBoundsException e, Model model) {
        log.error("IndexOutOfBoundsException: " + e.getMessage());
        model.addAttribute("info", "Вы пытаетесь обратиться к индексу, превышающему размер коллекции");
        model.addAttribute("message", e.getMessage());
        return "error";
    }

    @ExceptionHandler(IllegalStateException.class)
    public String illegalMoves(IllegalStateException e, Model model) {
        log.error("IllegalStateException: " + e.getMessage());
        model.addAttribute("info", "Вы делаете некорректное действие");
        model.addAttribute("message", e.getMessage());
        return "error";
    }


    @ExceptionHandler(SecurityException.class)
    public String security(SecurityException e, Model model) {
        log.error("IllegalStateException: " + e.getMessage());
        model.addAttribute("info", "Ах ты хулюган, хотел чужую тренировку испортить?!");
        model.addAttribute("message", "⺎ㄖ山ё人 廾闩〤э尸 ㄖт⼕ю具闩!!!");
        return "haker-error";
    }
}
