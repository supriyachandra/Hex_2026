package com.project.amazecare.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record ScheduleDto(
        Long id,
        LocalDate date,
        LocalTime startTime,
        LocalTime endTime
) {
}
