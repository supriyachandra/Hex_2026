package com.project.bookmanagement.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record BookResDto(

    long id,
    String name,
    String author_name,
    String ISBN,
    String publication_year
){}
