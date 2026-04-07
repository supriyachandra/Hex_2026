package com.project.amazecare.dto;

import com.project.amazecare.enums.AppointmentStatus;

import java.time.LocalDate;

public record FilterAppointmentDto(
        String name,
        AppointmentStatus status,
        LocalDate date
) {
}
