package com.project.amazecare.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record AddAdminDto(
        @NotNull
        String name,
        @NotNull
        @Size(min=3, max = 255)
        String username,
        @NotNull
        @Size(min=3, max = 255)
        String password
) {
}
