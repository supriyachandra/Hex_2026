package com.springboot.myapp.dto;

import java.math.BigDecimal;

public record PlanRespDto(
        String planName,
        BigDecimal price,
        int days
) {
}
