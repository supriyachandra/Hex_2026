package com.springboot.myapp.dto;

public record CustomerRespDto(
        long id,
        String name,
        String email,
        String city
) {
}
