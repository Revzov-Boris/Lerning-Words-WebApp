package com.example.learning_words_app.controllers;

import com.example.learning_words_app.Word;
import com.example.learning_words_app.entities.CategoryEntity;
import com.example.learning_words_app.entities.TwoFormWordEntity;
import com.example.learning_words_app.entities.WordEntity;
import com.example.learning_words_app.repositories.TwoFormWordRepository;
import com.example.learning_words_app.repositories.WordRepository;
import com.example.learning_words_app.services.CategoryService;
import com.example.learning_words_app.services.WordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.NoSuchElementException;

@Controller
@RequestMapping("/audio")
public class AudioController {
    @Autowired
    private WordRepository wordRepository;
    @Autowired
    private TwoFormWordRepository twoFormWordRepository;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private WordService wordService;


    @GetMapping("/{categoryId}/{wordId}/{formIndex}")
    public ResponseEntity<byte[]> getAudio(@PathVariable Integer categoryId,
                                           @PathVariable Integer wordId,
                                           @PathVariable Integer formIndex) {
        try {
            Word word = wordService.getByCategoryIdAndId(categoryId, wordId).orElseThrow();
            if (word.getForms().size() <= formIndex) {
                System.out.println("not found: " + "fromsize = " + word.getForms().size());
                throw new IndexOutOfBoundsException("The word have " + word.getForms().size() + " forms");
            }
            if (word.getForms().get(formIndex) == null) {
                return ResponseEntity.notFound().build();
            }
            System.out.println(word);

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType("audio/mpeg"))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline")
                    .body(word.getForms().get(formIndex).getAudioData());

        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping("/{wordId}")
    public ResponseEntity<byte[]> getAudio(@PathVariable Integer wordId) {
        try {
            WordEntity word = wordRepository.findById(wordId)
                    .orElseThrow(() -> new RuntimeException("Word not found"));

            if (word.getAudioData() == null) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType("audio/mpeg"))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline")
                    .body(word.getAudioData());

        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping("/secondform/{wordId}/{type}")
    public ResponseEntity<byte[]> getAudioFromSecond(@PathVariable Integer wordId,
                                                     @PathVariable String type) {
        try {
            TwoFormWordEntity word = twoFormWordRepository.findById(wordId)
                    .orElseThrow(() -> new RuntimeException("Word not found"));

            byte[] soundData = type.equals("1") ? word.getContentAudioData() : word.getSecondFormAudioData();
            if (soundData == null) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType("audio/mpeg"))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline")
                    .body(soundData);

        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
