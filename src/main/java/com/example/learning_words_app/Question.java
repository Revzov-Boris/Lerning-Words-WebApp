package com.example.learning_words_app;

import com.example.learning_words_app.entities.QuestionEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Question {
    Word word;
    int type;
    /**
     type:
     1 - перевод 1-й формы на русский
     2 - перевод 1-й формы с русского на английский
     3 - написать 1-ю форму на английском по звучанию

     4 - по 2-й форме написать 1-ю
     5 - перевод 2-й формы на русский
     6 - перевод 2-й формы с русского на английский
     7 - написать 2-ю форму на английском по звучанию

     8 - по 3-й форме написать 1-ю
     9 - перевод 3-й формы на русский
     10 - перевод 3-й формы с русского на английский
     11 - написать 3-ю форму на английском по звучанию
    */


    public String goodAnswer() {
        String result = null;
        switch (type) {
            case 1:
                result = word.getForms().get(0).getTranslation();
                break;
            case 2:
                result = word.getForms().get(0).getContent();
                break;
            case 3:
                result = word.getForms().get(0).getContent();
                break;
            case 4:
                result = word.getForms().get(0).getContent();
                break;
            case 5:
                result = word.getForms().get(1).getTranslation();
                break;
            case 6:
                result = word.getForms().get(1).getContent();
                break;
            case 7:
                result = word.getForms().get(1).getContent();
                break;
            case 8:
                result = word.getForms().get(0).getContent();
                break;
            case 9:
                result = word.getForms().get(2).getTranslation();
                break;
            case 10:
                result = word.getForms().get(2).getContent();
                break;
            case 11:
                result = word.getForms().get(2).getContent();
                break;
        }
        return result;
    }

    public String toText() {
        String result = null;
        switch (type) {
            case 1 :
                result = String.format("Переводи слово \"%s\" (1-я форма) на русский", word.getForms().get(0).getContent());
                break;
            case 2 :
                result = String.format("Переводи слово \"%s\" на английский", word.getForms().get(0).getTranslation().split(" ")[0]);
                break;
            case 3 :
                result = "Напиши, какое слово 1-й формы произносится на аудио";
                break;
            case 4 :
                result = String.format("Дана 2-я форма: \"%s\". Напиши его 1-ю форму", word.getForms().get(1).getContent());
                break;
            case 5 :
                result = String.format("Переводи слово \"%s\" (2-я форма) на русский", word.getForms().get(1).getContent());
                break;
            case 6 :
                result = String.format("Переводи слово \"%s\" на английский", word.getForms().get(1).getTranslation().split(" ")[0]);
                break;
            case 7 :
                result = "Напиши, какое слово 2-й формы произносится на аудио";
                break;
            case 8 :
                result = String.format("Дана 3-я форма: \"%s\". Напиши его 1-ю форму", word.getForms().get(2).getContent());
                break;
            case 9 :
                result = String.format("Переведи слово \"%s\" (3-я форма) на русский", word.getForms().get(2).getContent());
                break;
            case 10:
                result = String.format("Переводи слово \"%s\" на английский", word.getForms().get(2).getTranslation().split(" ")[0]);
                break;
            case 11:
                result = "Напиши, какое слово 3-й формы произносится на аудио";
                break;
        }
        return result;
    }
}
