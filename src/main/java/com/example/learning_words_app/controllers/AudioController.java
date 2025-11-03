package com.example.learning_words_app.controllers;

import com.example.learning_words_app.Word;
import com.example.learning_words_app.services.WordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/audio")
public class AudioController {
    @Autowired
    private WordService wordService;


    @GetMapping("/{categoryId}/{wordId}/{formIndex}")
    public ResponseEntity<byte[]> getAudio(@PathVariable Integer categoryId,
                                           @PathVariable Integer wordId,
                                           @PathVariable Integer formIndex) {
        try {
            Word word = wordService.getByCategoryIdAndId(categoryId, wordId).orElseThrow();
            if (word.getForms().size() <= formIndex) {
                throw new IndexOutOfBoundsException("The word have " + word.getForms().size() + " forms");
            }
            if (word.getForms().get(formIndex) == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType("audio/mpeg"))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline")
                    .body(word.getForms().get(formIndex).getAudioData());
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
