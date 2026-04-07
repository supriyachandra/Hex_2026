package com.project.bookmanagement.dto;

import com.project.bookmanagement.enums.Role;

public record UserDto(
        String username,
        String password,
        Role role
) {
}
