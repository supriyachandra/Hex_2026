package com.project.amazecare.dto;

import com.project.amazecare.enums.VisitType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.time.LocalTime;

public record AdminBookDto(
        @NotNull
        Long patient_id,

        @NotNull
        LocalDate appointmentDate,

        @NotNull
        LocalTime timeSlot,

        @Size(min = 3, max = 1000)
        String symptoms,

        VisitType visitType

) {
}
