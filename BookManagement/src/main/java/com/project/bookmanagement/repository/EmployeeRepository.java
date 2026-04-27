package com.project.bookmanagement.repository;

import com.project.bookmanagement.enums.JobTitle;
import com.project.bookmanagement.enums.Status;
import com.project.bookmanagement.model.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    @Query("select e from Employee e where e.company.status= ?1 and e.jobTitle=?2")
    Page<Employee> getEmployeeBy(Status status, JobTitle jobTitle, Pageable pageable);
}
