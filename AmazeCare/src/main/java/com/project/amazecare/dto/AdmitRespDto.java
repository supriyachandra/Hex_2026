package com.project.amazecare.dto;

import com.project.amazecare.enums.AdmissionStatus;
import com.project.amazecare.enums.PatientType;
import jakarta.persistence.Column;

import java.time.LocalDate;

public record AdmitRespDto(
        Long reference_id,
        Long patient_id,
        String patient_name,
        Long doctor_id,
        String doctor_specialization,
        String doctor_name,
        String reason,
        String room_no,
        String bed_no,
        LocalDate admission_date,
        LocalDate discharge_date,
        AdmissionStatus admission_status,
        PatientType patient_type
) {
}
