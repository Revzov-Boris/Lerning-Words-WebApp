//package com.example.learning_words_app;
//
//
//import com.example.learning_words_app.entities.CategoryEntity;
//import com.example.learning_words_app.entities.ThreeFormWordEntity;
//import com.example.learning_words_app.entities.TwoFormWordEntity;
//import com.example.learning_words_app.entities.WordEntity;
//import com.example.learning_words_app.repositories.CategoryRepository;
//import com.example.learning_words_app.repositories.ThreeFormWordRepository;
//import com.example.learning_words_app.repositories.TwoFormWordRepository;
//import com.example.learning_words_app.repositories.WordRepository;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.io.File;
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Scanner;
//
//@Configuration
//public class DataInitializer {
//
//    @Bean
//    public CommandLineRunner initData(CategoryRepository categoryRepository,
//                                      WordRepository wordRepository,
//                                      TwoFormWordRepository twoFormWordRepository,
//                                      ThreeFormWordRepository threeFormWordRepository) {
//        return args -> {
//
//            Path textWordsPath = Paths.get("D:\\My projects\\Python projects\\lerning inglish words\\LearningInglishWords\\words.txt");
//            Path audioDir = Paths.get("D:\\My projects\\Python projects\\lerning inglish words\\LearningInglishWords\\Nast");
//            Path audioTwoFormDir = Paths.get("D:\\My projects\\Python projects\\lerning inglish words\\LearningInglishWords\\Last");
//            Path audioThreeFormDir = Paths.get("D:\\My projects\\Python projects\\lerning inglish words\\LearningInglishWords\\Third");
//            Scanner scanner = new Scanner(new File(textWordsPath.toString()));
//
//            // добавление в таблицу words
////            List<WordEntity> words = new ArrayList<>();
////            CategoryEntity category = categoryRepository.getById(5);
////            while (scanner.hasNextLine()) {
////                String str = scanner.nextLine();
////                List<String> strings = List.of(str.split("; "));
////                WordEntity word = new WordEntity(strings.get(0), strings.get(2), strings.get(4), category);
////                Path pathToAudio = Paths.get(audioDir + "\\" + word.getContent() + "_n.mp3");
////                try {
////                    byte[] audioData = Files.readAllBytes(pathToAudio);
////                    word.setAudioData(audioData);
////                    System.out.println("Loaded audio for: " + word.getContent());
////                } catch (Exception e) {
////                    System.err.println("Error loading audio for " + word.getContent() + ": " + e.getMessage());
////                    word.setAudioData(null);
////                }
////                wordRepository.save(word);
////            }
//
//
//            // добавление в таблицу two_form_words
////            Path audioTwoFormDir = Paths.get("D:\\My projects\\Python projects\\lerning inglish words\\LearningInglishWords\\Last");
////            List<TwoFormWordEntity> twoFormWords = new ArrayList<>();
////            CategoryEntity category2 = categoryRepository.getById(6);
////            while (scanner.hasNextLine()) {
////                String str = scanner.nextLine();
////                List<String> strings = List.of(str.split("; "));
////                TwoFormWordEntity word = new TwoFormWordEntity(strings.get(0), strings.get(2), strings.get(4), strings.get(1), strings.get(3), strings.get(5), category2);
////                Path pathToAudio = Paths.get(audioDir + "\\" + word.getContent() + "_n.mp3");
////                Path pathToTwoAudio = Paths.get(audioTwoFormDir + "\\" + word.getSecondForm() + "_p.mp3");
////                try {
////                    byte[] audioData = Files.readAllBytes(pathToAudio);
////                    byte[] audioData2 = Files.readAllBytes(pathToTwoAudio);
////                    word.setContentAudioData(audioData);
////                    word.setSecondFormAudioData(audioData2);
////                    System.out.println("Loaded audio for: " + word.getContent());
////                } catch (Exception e) {
////                    System.err.println("Error loading audio for " + word.getContent() + ": " + e.getMessage());
////                    word.setContentAudioData(null);
////                    word.setSecondFormAudioData(null);
////                }
////                twoFormWordRepository.save(word);
////            }
//
//
//
//            // добавление в таблицу three_form_words
////            List<TwoFormWordEntity> twoFormWords = new ArrayList<>();
////            CategoryEntity category2 = categoryRepository.getById(8);
////            while (scanner.hasNextLine()) {
////                String str = scanner.nextLine();
////                List<String> strings = List.of(str.split("; "));
////                ThreeFormWordEntity word = new ThreeFormWordEntity(strings.get(0), strings.get(3), strings.get(6), strings.get(1), strings.get(4), strings.get(7), strings.get(2), strings.get(5), strings.get(8), category2);
////                Path pathToAudio = Paths.get(audioDir + "\\" + word.getContent() + "_n.mp3");
////                Path pathToTwoAudio = Paths.get(audioTwoFormDir + "\\" + word.getSecondForm() + "_p.mp3");
////                Path pathToThreeAudio = Paths.get(audioThreeFormDir + "\\" + word.getThirdForm() + "_t.mp3");
////                try {
////                    byte[] audioData = Files.readAllBytes(pathToAudio);
////                    byte[] audioData2 = Files.readAllBytes(pathToTwoAudio);
////                    word.setContentAudioData(audioData);
////                    word.setSecondFormAudioData(audioData2);
////                    if (Files.exists(pathToThreeAudio)) {
////                        byte[] audioData3 = Files.readAllBytes(pathToThreeAudio);
////                        word.setThirdFormAudioData(audioData3);
////                    }
////
////                    System.out.println("Loaded audio for: " + word.getContent());
////                } catch (Exception e) {
////                    System.err.println("Error loading audio for " + word.getContent() + ": " + e.getMessage());
////                    word.setContentAudioData(null);
////                    word.setSecondFormAudioData(null);
////                }
////
////                threeFormWordRepository.save(word);
////            }
//
//            // добавление слов с нестандартной формой множественного числа
//            CategoryEntity category4 = categoryRepository.getById(9);
//            Path pathToBadPlural = Paths.get("D:\\My projects\\Java projects\\learning-words-app\\src\\main\\resources\\mnforms.txt");
//            Path pathToAudioPlural = Paths.get("D:\\sounds");
//            Scanner scannerBadPlural = new Scanner(new File(pathToBadPlural.toString()));
//            while (scannerBadPlural.hasNextLine()) {
//                String str = scannerBadPlural.nextLine();
//                List<String> strings = List.of(str.split("; "));
//                TwoFormWordEntity word = new TwoFormWordEntity(strings.get(0), strings.get(2), strings.get(4), strings.get(1), strings.get(3), strings.get(5), category4);
//                Path pathToAudio1 = Paths.get(pathToAudioPlural + "\\" + word.getContent() + ".mp3");
//                Path pathToAudio2 = Paths.get(pathToAudioPlural + "\\" + word.getSecondForm() + ".mp3");
//                try {
//                    byte[] audioData = Files.readAllBytes(pathToAudio1);
//                    byte[] audioData2 = Files.readAllBytes(pathToAudio2);
//                    word.setContentAudioData(audioData);
//                    word.setSecondFormAudioData(audioData2);
//                } catch (IOException e) {
//                    System.out.println(e.getMessage());
//                }
//                twoFormWordRepository.save(word);
//
//            }
//
//
//        };
//    }
//}
