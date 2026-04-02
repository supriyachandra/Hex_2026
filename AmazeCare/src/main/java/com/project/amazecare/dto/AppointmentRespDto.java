package com.project.amazecare.dto;

import com.project.amazecare.enums.AppointmentStatus;

import java.time.LocalDate;

public record AppointmentRespDto(
        long app_id,
        LocalDate app_date,
        long patient_id,
        String patient_name,
        long doctor_id,
        String doctor_specialization,
        String doctor_name,
        AppointmentStatus status
) {
}
