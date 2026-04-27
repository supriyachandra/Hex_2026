package com.springboot.myapp.dto;

import com.springboot.myapp.enums.TicketStatus;

public record StatsDto(
        String status,
        int count
) {
}
