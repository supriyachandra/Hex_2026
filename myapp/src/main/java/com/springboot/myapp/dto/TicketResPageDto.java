package com.springboot.myapp.dto;

import com.springboot.myapp.model.Ticket;

import java.util.List;

public record TicketResPageDto(
        List<TicketRespDto> list,
        Long totalRecord,
        int totalPage
) {
}
