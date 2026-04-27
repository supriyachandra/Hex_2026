package com.springboot.myapp.dto;

import com.springboot.myapp.enums.Role;

public record UserDto(
        String username,
        Role role
) {
}
