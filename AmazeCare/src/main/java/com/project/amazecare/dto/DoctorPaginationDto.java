package com.project.amazecare.dto;

import java.util.List;

public record DoctorPaginationDto(
        List<DoctorRespDto> data,
        Long totalRecords,
        int totalPages
) {
}
