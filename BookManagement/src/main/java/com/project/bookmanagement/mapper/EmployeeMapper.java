package com.project.bookmanagement.mapper;

import com.project.bookmanagement.dto.EmployeeResDto;
import com.project.bookmanagement.dto.EmployeeResPageDto;
import com.project.bookmanagement.model.Employee;

public class EmployeeMapper {
    public static EmployeeResDto mapToDto(Employee employee){
        return new EmployeeResDto(
                employee.getName(),
                employee.getJobTitle(),
                employee.getCompany().getName(),
                employee.getCompany().getStatus()
        );
    }
}
