package com.example.learning_words_app;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Question {
    private static final List<Integer> typesWithAudio = List.of(3, 7, 11);

    private Word word;
    private int type;
    /**
     type:<br>
     1 - перевод 1-й формы на русский<br>
     2 - перевод 1-й формы с русского на английский<br>
     3 - написать 1-ю форму на английском по звучанию<br>
     <br>
     4 - по 2-й форме написать 1-ю<br>
     5 - перевод 2-й формы на русский<br>
     6 - перевод 2-й формы с русского на английский<br>
     7 - написать 2-ю форму на английском по звучанию<br>
     <br>
     8 - по 3-й форме написать 1-ю<br>
     9 - перевод 3-й формы на русский<br>
     10 - перевод 3-й формы с русского на английский<br>
     11 - написать 3-ю форму на английском по звучанию<br>
    */


    public String goodAnswer() {
        switch (type) {
            case 1:
                return word.getForms().getFirst().getTranslation();
            case 2, 3, 4:
                return word.getForms().getFirst().getContent();
            case 5:
                return word.getForms().get(1).getTranslation();
            case 6, 7:
                return word.getForms().get(1).getContent();
            case 8:
                return word.getForms().get(0).getContent();
            case 9:
                return word.getForms().get(2).getTranslation();
            case 10, 11:
                return word.getForms().get(2).getContent();
            default:
                return "";
        }
    }

    public String toText() {
        List<String> formsInfo = word.getCategory().getFormsInfo();
        String info;
        String wordStr;
        switch (type) {
            case 1 :
                info = (formsInfo != null) ? String.format("(%s)", formsInfo.getFirst()) : "";
                wordStr = word.getForms().get(0).getContent();
                return String.format("Переводи слово \"%s\" %s на русский", wordStr, info);
            case 2 :
                wordStr = word.getForms().get(0).getTranslation().split(" ")[0];
                return String.format("Переводи слово \"%s\" на английский", wordStr);
            case 3 :
                info = (formsInfo != null) ? String.format("(%s)", formsInfo.getFirst()) : "";
                return String.format("Напиши, какое слово произносится на аудио %s", info);
            case 4 :
                info = formsInfo.get(1);
                wordStr = word.getForms().get(1).getContent();
                return String.format("Дано слово: \"%s\" (%s). Как пишется его %s?", wordStr, info, formsInfo.getFirst());
            case 5 :
                info = formsInfo.get(1);
                wordStr = word.getForms().get(1).getContent();
                return String.format("Переводи слово \"%s\" (%s) на русский", wordStr, info);
            case 6 :
                wordStr = word.getForms().get(1).getTranslation().split(" ")[0];
                return String.format("Переводи слово \"%s\" на английский", wordStr);
            case 7 :
                info = String.format("(%s)", formsInfo.get(1));
                return String.format("Напиши, какое слово произносится на аудио %s", info);
            case 8 :
                info = formsInfo.get(2);
                wordStr = word.getForms().get(2).getContent();
                return String.format("Дано слово: \"%s\" (%s). Как пишется его %s?", wordStr, info, formsInfo.getFirst());
            case 9 :
                info = formsInfo.get(2);
                wordStr = word.getForms().get(2).getContent();
                return String.format("Переведи слово \"%s\" (%s) на русский", wordStr, info);
            case 10:
                wordStr = word.getForms().get(2).getTranslation().split(" ")[0];
                return String.format("Переводи слово \"%s\" на английский", wordStr);
            case 11:
                info = String.format("(%s)", formsInfo.get(2));
                return String.format("Напиши, какое слово произносится на аудио %s", info);
            default:
                return "";
        }
    }

    public boolean hasAudio() {
        return typesWithAudio.contains(type);
    }

    public int formIndexByType() {
        return typesWithAudio.indexOf(type);
    }
}
