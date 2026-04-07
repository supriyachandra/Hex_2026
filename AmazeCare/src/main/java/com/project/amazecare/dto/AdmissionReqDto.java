package com.project.amazecare.dto;

import java.time.LocalDate;

public record AdmissionReqDto(
        long patient_id,
        long doctor_id,
        String reason,
        String roomNumber,
        String bedNumber
) {
}
