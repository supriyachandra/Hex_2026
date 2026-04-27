package com.project.amazecare.dto;

import java.util.List;

public record PatientPaginationDto(
        List<CreatePatientDto> data,
        Long totalRecords,
        int totalPages
) {
}
