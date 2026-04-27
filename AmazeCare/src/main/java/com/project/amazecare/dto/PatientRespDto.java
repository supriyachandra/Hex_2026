package com.project.amazecare.dto;

import com.project.amazecare.enums.Gender;

import java.time.LocalDate;

public record PatientRespDto(
        long id,
        String name,
        LocalDate dob,
        Gender gender,
        String phone
) {
}
