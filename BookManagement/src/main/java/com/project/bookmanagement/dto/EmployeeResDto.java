package com.project.bookmanagement.dto;

import com.project.bookmanagement.enums.JobTitle;
import com.project.bookmanagement.enums.Status;

public record EmployeeResDto(
        String employee_name,
        JobTitle jobTitle,
        String company_name,
        Status status
) {
}
