package com.springboot.myapp.controller;

import com.springboot.myapp.dto.OllamaResponse;
import com.springboot.myapp.dto.PromptDto;
import com.springboot.myapp.service.OllamaService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/ollama")
public class OllamaController {

    private final OllamaService ollamaService;

    @PostMapping("/chat")
    // Flux<String>
    public ResponseEntity<OllamaResponse> promptAPI(
            @Valid @RequestBody PromptDto promptDto){
        return
                ResponseEntity
                        .ok(ollamaService.chat(promptDto));
    }
}
