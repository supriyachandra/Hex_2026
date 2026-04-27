package com.project.amazecare.dto;

import com.project.amazecare.enums.AppointmentStatus;

import java.time.LocalDate;
import java.time.LocalTime;

public record AppointmentRespDto(
        Long app_id,
        LocalDate app_date,
        LocalTime app_time_slot,
        long patient_id,
        String patient_name,
        long doctor_id,
        String doctor_specialization,
        String doctor_name,
        AppointmentStatus status,
        String symptoms
) {
}
