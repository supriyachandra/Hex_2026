package com.springboot.myapp.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ActivePlanDto(
        String planName,
        BigDecimal price,
        int days,
        LocalDate startDate,
        LocalDate endDate

) {
}
