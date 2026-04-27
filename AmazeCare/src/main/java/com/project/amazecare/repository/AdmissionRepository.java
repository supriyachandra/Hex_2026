package com.project.amazecare.repository;

import com.project.amazecare.enums.AdmissionStatus;
import com.project.amazecare.model.Admission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Transactional
    @Modifying
    @Query("""
        update Admission a set a.status= 'DISCHARGE_REQUESTED'
                where a.id= ?1
        """)
    void requestDischarge(long admissionId);

    @Query("select a from Admission a where a.dischargeDate is null")
    Page<Admission> getAllActive(Pageable pageable);

    @Query("select a from Admission a where a.status='DISCHARGED'")
    Page<Admission> getAllPast(Pageable pageable);

    @Query("select a from Admission a where a.status=?2 and a.doctor.user.username=?1")
    Page<Admission> getAllPastDoc(Pageable pageable, String username, AdmissionStatus status);

    @Query("select a from Admission a where a.dischargeDate is null and a.doctor.user.username=?1")
    Page<Admission> getAllActiveDoc(Pageable pageable, String username);

    @Query("select count(a.id) from Admission a where a.dischargeDate is null")
    Long countCurrentlyAdmitted();
}
