package com.springboot.myapp.dto;

import java.util.List;

public record TicketByCustomerDto(
        List<TicketCustomerDto> data,
        long totalRecords,
        int totalPages
) {
}
