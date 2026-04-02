package com.project.amazecare.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record DoctorSignUpDto(
        @NotNull
        @Size(min=2, max=255)
        String name,
        @NotNull

        int experience,
        @NotNull
        @NotBlank
        String phone,

        @NotBlank
        @NotNull
        @Size(min=4, max=255)
        String email,

        @NotNull
        @NotBlank
        String qualification,
        String designation,

        @NotNull
        long specialization_id,

        @NotBlank
        String username,

        @NotNull
        String password
) {
}
