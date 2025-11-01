package com.example.learning_words_app;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Arrays;

@Setter @Getter
@NoArgsConstructor @AllArgsConstructor
public class FormWord {
    private String content;
    private String translation;
    private String transcription;
    private byte[] audioData;


    @Override
    public String toString() {
        return "FormWord{" +
                "content='" + content + '\'' +
                ", translation='" + translation + '\'' +
                ", transcription='" + transcription + '\'' +
                '}';
    }
}
