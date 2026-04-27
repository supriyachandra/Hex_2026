package com.project.bookmanagement.controller;

import com.project.bookmanagement.dto.EmployeeResPageDto;
import com.project.bookmanagement.enums.Status;
import com.project.bookmanagement.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/employee")
@AllArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;
    @GetMapping("/get-all")
    public EmployeeResPageDto getEmployeesBy(@RequestParam(value = "page", defaultValue = "0", required = false) int page,
                                             @RequestParam(value = "size", defaultValue = "5", required = false) int size){
        String status= "ACTIVE";
        String jobTitle= "DEV";
        return employeeService.getEmployeesBy(page, size, status, jobTitle);
    }
}
//Create a GET API to fetch employees having jobTitle 'DEV' that belong to ACTIVE company
//Do validation
//Do pagination
//Use DTO for response
//response structure:- employee_name, jobTitle, company_name, status
