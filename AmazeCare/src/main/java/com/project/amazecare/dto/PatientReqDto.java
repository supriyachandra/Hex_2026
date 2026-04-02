package com.project.amazecare.dto;

import com.project.amazecare.enums.Gender;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record PatientReqDto(
        @NotNull
        @NotBlank
        @Size(min=2, max = 255)
        String name,

        @NotNull
        LocalDate DOB,

        Gender gender,
        @NotNull
        @NotBlank
        String phone
) {
}
