package com.springboot.myapp.dto;

import jakarta.validation.constraints.NotBlank;

public record PromptDto(
        @NotBlank(message = "Please ask something to expect a response")
        String prompt
) {
}
