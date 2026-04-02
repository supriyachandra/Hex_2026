package com.project.amazecare.dto;

public record DoctorRespDto(
        String name,
        int experience,
        String email,
        String qualification,
        String designation,
        String specialization
) {
}
