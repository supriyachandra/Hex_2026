package com.project.amazecare.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record ScheduleDto(
        LocalDate date,
        LocalTime startTime,
        LocalTime endTime,
        Long doctor_id
) {
}
