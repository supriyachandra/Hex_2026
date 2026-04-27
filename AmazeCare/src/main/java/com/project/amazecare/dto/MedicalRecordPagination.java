package com.project.amazecare.dto;

import java.util.List;

public record MedicalRecordPagination(
        List<MedicalRecordDto> data,
        Long totalRecords,
        int totalPages
) {
}
