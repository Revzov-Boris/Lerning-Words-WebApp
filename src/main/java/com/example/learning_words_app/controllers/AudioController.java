package com.example.learning_words_app.controllers;

import com.example.learning_words_app.services.FormWordService;
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
    private FormWordService formWordService;


    @GetMapping("/{wordId}/{formIndex}")
    public ResponseEntity<byte[]> getAudio(@PathVariable Integer wordId, @PathVariable Integer formIndex) {
        byte[] audioData = formWordService.getAudioDataByWordAndNumber(wordId, formIndex);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("audio/mpeg"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline")
                .body(audioData);
    }
}
