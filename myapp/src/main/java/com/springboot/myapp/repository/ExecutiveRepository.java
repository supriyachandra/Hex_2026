package com.springboot.myapp.repository;

import com.springboot.myapp.dto.ExecutiveDto;
import com.springboot.myapp.enums.JobTitle;
import com.springboot.myapp.model.Executive;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ExecutiveRepository extends JpaRepository<Executive, Long> {
    @Query("""
    select e.name, e.jobTitle from Executive e where e.jobTitle= ?1
    """)
    ExecutiveDto findByJobTitle(JobTitle jobTitle);
}
