package com.project.amazecare.dto;

import com.project.amazecare.enums.Role;

public record UserDetailsDto(
        String username,
        Role role
) {
}
