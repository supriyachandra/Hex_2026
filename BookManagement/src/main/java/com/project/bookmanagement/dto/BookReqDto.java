package com.project.bookmanagement.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record BookReqDto(
        @NotNull
        @NotBlank
        String name,

        @NotNull
        @Size(min = 3, max = 355)
        String author_name,

        @NotNull
        @Size(min = 5, max = 100)
        @Column(unique = true)
        String ISBN,
        String publication_year,

        long author_id
) {
}
