package com.hibernate.dto;

import com.hibernate.model.Employee;

import java.util.List;

public record FlightDto(
        String flightNumber,
        String source,
        String destination,
        String airline_name,
        List<Employee> employees
) {
}
