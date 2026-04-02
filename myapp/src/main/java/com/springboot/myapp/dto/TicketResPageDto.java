package com.springboot.myapp.dto;

import com.springboot.myapp.model.Ticket;

import java.util.List;

public record TicketResPageDto(
        List<TicketRespDto> data,
        Long totalRecord,
        int totalPage
) {
}
