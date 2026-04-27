package com.project.amazecare.dto;

import java.util.List;

public record AdmissionsPagination(
        List<AdmitRespDto> data,
        long totalRecords,
        int totalPages
) {
}
