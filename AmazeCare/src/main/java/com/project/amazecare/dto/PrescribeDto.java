package com.project.amazecare.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PrescribeDto(
        @NotNull
        long consultation_id,
        @NotNull
        @NotBlank
        String dosage,
        @NotNull
        @NotBlank
        String medicine_name
) {
}
