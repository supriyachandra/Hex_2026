package com.springboot.myapp.dto;

public record DocumentDto(
        long id,
        String customerName,
        String profileImage
) {
}
