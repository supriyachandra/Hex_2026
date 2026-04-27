package com.springboot.myapp.controller;

import com.springboot.myapp.dto.DocumentDto;
import com.springboot.myapp.service.DocumentService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;

@RestController
@RequestMapping("/api/document")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:5173/")
public class DocumentController {

    private final DocumentService documentService;

    @PostMapping("/upload")
    public DocumentDto upload(Principal principal,
                              @RequestParam("file") MultipartFile file) throws IOException{
        String customerUsername= principal.getName();
        return documentService.upload(customerUsername, file);
    }
}
