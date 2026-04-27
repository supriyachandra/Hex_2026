package com.project.bookmanagement.service;

import com.project.bookmanagement.exception.ResourceNotFoundException;
import com.project.bookmanagement.model.Author;
import com.project.bookmanagement.repository.AuthorRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthorService {
    private final AuthorRepository authorRepository;
    public Author findById(long authorId) {
        return authorRepository.findById(authorId)
                .orElseThrow(()-> new ResourceNotFoundException("Author ID Invalid"));
    }

    public Author findByUsername(String username) {
        return authorRepository.findByUsername(username);
    }
}
