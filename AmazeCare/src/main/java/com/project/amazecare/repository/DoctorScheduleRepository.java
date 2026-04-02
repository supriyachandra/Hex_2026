package com.project.amazecare.repository;

import com.project.amazecare.model.DoctorSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DoctorScheduleRepository extends JpaRepository<DoctorSchedule, Long> {

    @Query("select ds from DoctorSchedule ds where ds.doctor.id= ?1")
    List<DoctorSchedule> findByDoctorId(long doctorId);
}
