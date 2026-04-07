package com.project.amazecare.dto;


public record ConsultReqDto(
        String examination,
        String diagnosis,
        String treatmentPlan,
        String symptomNotes,
        Long appointment_id,
        Long admission_id
) {
}
