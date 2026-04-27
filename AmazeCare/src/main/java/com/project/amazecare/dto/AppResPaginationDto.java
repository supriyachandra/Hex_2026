package com.project.amazecare.dto;

import java.util.List;

public record AppResPaginationDto(
        List<AppointmentRespDto> data,
        Long totalRecords,
        int totalPages
) {
}
