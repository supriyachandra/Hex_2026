package com.springboot.myapp.dto;

import com.springboot.myapp.enums.JobTitle;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ExecutiveDto(
        @NotNull
        @NotBlank
        @Size(min=2, max = 255)
        String name,

        JobTitle jobTitle
) {
}
