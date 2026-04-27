package com.project.amazecare.repository;

import com.project.amazecare.model.Patient;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PatientRepository extends JpaRepository<Patient, Long> {

    @Query("select p from Patient p where p.user.username=?1")
    Patient getPatient(String username);

    @Query("select count(p.id) from Patient p")
    Long totalPatients();
}
