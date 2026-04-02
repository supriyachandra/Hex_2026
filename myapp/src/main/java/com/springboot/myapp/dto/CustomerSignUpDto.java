package com.springboot.myapp.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CustomerSignUpDto(
        @NotBlank
        @NotNull
        @Size(min=2, max=255)
        String name,

        @Email
        String email,

        String city,

        @NotBlank
        @NotNull
        @Size(min=2, max=100)
        String username,
        String password
) {
}
