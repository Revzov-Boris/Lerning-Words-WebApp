package com.example.learning_words_app;


import com.example.learning_words_app.entities.*;
import com.example.learning_words_app.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Configuration
public class DataInitializer {
    @Bean
    public CommandLineRunner initData(CategoryRepository categoryRepository,
                                      WordRepository wordRepository) {
        return args -> {

            Path textWordsPath = Paths.get("D:\\My projects\\Python projects\\lerning inglish words\\LearningInglishWords\\words.txt");
            Path audioDir = Paths.get("D:\\My projects\\Python projects\\lerning inglish words\\LearningInglishWords\\Nast");
            Path audioTwoFormDir = Paths.get("D:\\My projects\\Python projects\\lerning inglish words\\LearningInglishWords\\Last");
            Path audioThreeFormDir = Paths.get("D:\\My projects\\Python projects\\lerning inglish words\\LearningInglishWords\\Third");
            Scanner scanner = new Scanner(new File(textWordsPath.toString()));



        };
    }
}
