package com.project.amazecare.repository;

import com.project.amazecare.model.Admission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface AdmissionRepository extends JpaRepository<Admission, Long> {

    @Query("select count(a) from Admission a where a.patient.id= ?1 and a.dischargeDate is null")
    long patientAlreadyAdmitted(Long id);

    @Modifying
    @Transactional
    @Query("""
        update Admission a set a.dischargeDate= current_date,
        a.status= 'DISCHARGED'
        where a.id= ?1
        """)
    void dischargePatient(long admissionId);
}
