package com.project.bookmanagement.service;

import com.project.bookmanagement.dto.EmployeeResDto;
import com.project.bookmanagement.dto.EmployeeResPageDto;
import com.project.bookmanagement.enums.JobTitle;
import com.project.bookmanagement.enums.Status;
import com.project.bookmanagement.mapper.EmployeeMapper;
import com.project.bookmanagement.model.Employee;
import com.project.bookmanagement.repository.EmployeeRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    public EmployeeResPageDto getEmployeesBy(int page, int size,String s_status,  String s_jobTitle) {
        Status status= Status.valueOf(s_status);
        JobTitle jobTitle= JobTitle.valueOf(s_jobTitle);

        Pageable pageable= PageRequest.of(page, size);

        Page<Employee> employeePage= employeeRepository.getEmployeeBy(status, jobTitle, pageable);

        List<EmployeeResDto> employeeResDtoList= employeePage.stream()
                .map(EmployeeMapper::mapToDto)
                .toList();

        long records= employeePage.getNumberOfElements();
        int pages= employeePage.getTotalPages();

        return new EmployeeResPageDto(
            employeeResDtoList,
            records,
            pages
        );
    }
}
