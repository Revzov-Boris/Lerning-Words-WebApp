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
    private Word word;
    private int type;
    /**
     <b>type:</b><br>
     1: перевод 1-й формы на русский<br>
     2: перевод 1-й формы с русского на ин. яз<br>
     3: написать 1-ю форму по звучанию<br>
     <br>
     4: по 2-й форме написать 1-ю<br>
     5: перевод 2-й формы на русский<br>
     6: перевод 2-й формы с русского на ин. яз<br>
     7: написать 2-ю форму на ин. яз по звучанию<br>
     ...
     <br>
     4n: по (n+1)-й форме написать 1-ю<br>
     4n + 1: перевод (n+1)-й формы на русский<br>
     4n + 2: перевод (n+1)-й формы с русского на ин. яз<br>
     4n + 3: написать (n+1)-ю форму на ин. яз по звучанию<br>
    */


    public static boolean isAudioType(int type) {
        return type % 4 == 3;
    }


    public static int formIndexByType(int type) {
        return type / 4;
    }


    public String goodAnswer() {
        int formIndex = type / 4;
        return switch (type % 4) {
            case 0 -> word.getForms().getFirst().getContent();
            case 1 -> word.getForms().get(formIndex).getTranslation();
            case 2, 3 -> word.getForms().get(formIndex).getContent();
            default -> "";
        };
    }


    public String toText() {
        List<String> formsInfo = word.getCategory().getFormsInfo();
        String info = formsInfo != null ? String.format("(%s)", formsInfo.get(type / 4)) : "";;
        String wordStr;
        switch (type % 4) {
            case 0:
                wordStr = word.getForms().get(type / 4).getContent();
                return String.format("Дано слово: \"%s\" %s. Как пишется его %s?", wordStr, info, formsInfo.getFirst());
            case 1:
                wordStr = word.getForms().get(type / 4).getContent();
                return String.format("Переводи слово \"%s\" %s на русский", wordStr, info);
            case 2:
                wordStr = word.getForms().get(type / 4).getTranslation().split(" ")[0];
                return String.format("Переводи слово \"%s\" на ин. яз", wordStr);
            case 3:
                return String.format("Напиши, какое слово произносится на аудио %s", info);
            default:
                return "";
        }
    }


    public boolean hasAudio() {
        return isAudioType(type);

    }


    public int getFormIndex() {
        return formIndexByType(type);
    }
}
