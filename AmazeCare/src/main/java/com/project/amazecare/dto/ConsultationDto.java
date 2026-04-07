package com.project.amazecare.dto;

public record ConsultationDto(
        String examination,
        String diagnosis,
        String treatmentPlan,
        String symptomNotes
) {
}
