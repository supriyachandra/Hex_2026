package com.project.amazecare.dto;

import com.project.amazecare.enums.Gender;
import com.project.amazecare.enums.PatientType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record CreatePatientDto(
        @NotNull
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
