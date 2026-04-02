package com.project.amazecare.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record ScheduleRespDto(
        String doctor_name,
        LocalDate date,
        LocalTime start_time,
        LocalTime end_time
) {
}
