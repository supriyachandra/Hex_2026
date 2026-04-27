package com.project.amazecare.dto;

public record DoctorRespDto(
        long id,
        String name,
        int experience,
        String email,
        String qualification,
        String designation,
        String specialization
) {
}
