package com.project.amazecare.dto;

import com.project.amazecare.enums.PatientType;

import java.time.LocalDate;
import java.util.List;

public record MedicalRecordDto(
        long reference_id,
        PatientType patientType,
        String patient_name,
        String doctor_name,
        LocalDate date,
        List<ConsultationDto> consultationDtoList,
        List<PrescriptionDto> prescriptionDtoList,
        List<TestDto> testDtoList
) {
}
