package com.project.amazecare.dto;

import jakarta.validation.constraints.NotNull;

public record ConsultReqDto(
        String examination,
        String diagnosis,
        String treatmentPlan,
        String symptomNotes,
        @NotNull
        long appointment_id
) {
}
