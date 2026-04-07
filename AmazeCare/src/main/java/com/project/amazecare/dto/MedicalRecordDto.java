package com.project.amazecare.dto;

import com.project.amazecare.enums.PatientType;

import java.time.LocalDate;
import java.util.List;

public record MedicalRecordDto(
        PatientType patientType,
        String doctor_name,
        LocalDate date,
        List<ConsultationDto> consultationDtoList,
        List<PrescriptionDto> prescriptionDtoList,
        List<TestDto> testDtoList
) {
}
