package com.springboot.myapp.dto;

import java.time.LocalDate;

public record CustomerByPlanDto(
    long customerId,
    String name,
    String email,
    String city,
    LocalDate start_date,
    LocalDate end_date,
    String plan_name
)
{ }
