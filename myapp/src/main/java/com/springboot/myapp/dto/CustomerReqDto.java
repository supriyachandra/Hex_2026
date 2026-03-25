package com.springboot.myapp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CustomerReqDto(
        @NotBlank
        @NotNull
        @Size(min=2, max=255)
        String name,

        @NotBlank
        @NotNull
        @Size(min=5, max=255)
        String email,

        @NotBlank
        @NotNull
        @Size(min=2, max=255)
        String city
) {

}
