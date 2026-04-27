package com.springboot.myapp.dto;

import com.springboot.myapp.enums.TicketStatus;

public record StatsDtoV2(
        TicketStatus status,
        Long count
) {
}
