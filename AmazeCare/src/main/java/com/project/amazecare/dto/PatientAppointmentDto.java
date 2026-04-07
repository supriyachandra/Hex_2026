package com.project.amazecare.dto;

import com.project.amazecare.enums.AppointmentStatus;

import java.time.LocalDate;
import java.time.LocalTime;

public record PatientAppointmentDto(
        String name,
        LocalDate date,
        LocalTime time,
        AppointmentStatus status
) {
}
