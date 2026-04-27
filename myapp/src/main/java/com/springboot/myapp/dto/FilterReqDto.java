package com.springboot.myapp.dto;

import com.springboot.myapp.enums.TicketPriority;
import com.springboot.myapp.enums.TicketStatus;

public record FilterReqDto(
        TicketPriority priority,
        TicketStatus status
) {
}
