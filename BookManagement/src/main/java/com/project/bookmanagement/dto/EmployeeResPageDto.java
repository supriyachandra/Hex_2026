package com.project.bookmanagement.dto;

import java.util.List;

public record EmployeeResPageDto(
        List<EmployeeResDto> data,
        long totalRecords,
        int totalPages
) {
}
