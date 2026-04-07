package com.project.amazecare.dto;

import jakarta.validation.constraints.NotNull;

public record TestReqDto(
        @NotNull
        long consultation_id,
        String test_name
) {
}
