package com.project.amazecare.dto;


import java.util.List;

public record ConsultReqDto(
        String examination,
        String diagnosis,
        String treatmentPlan,
        String symptomNotes,
        Long appointment_id,
        Long admission_id,
        List<PrescriptionDto> prescriptions,
        List<TestReqDto> tests
) {
}
