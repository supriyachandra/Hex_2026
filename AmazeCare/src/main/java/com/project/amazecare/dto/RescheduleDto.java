package com.project.amazecare.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record RescheduleDto(
        LocalDate date,
        LocalTime time
) {
}
