package com.project.amazecare.repository;

import com.project.amazecare.model.DoctorSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface DoctorScheduleRepository extends JpaRepository<DoctorSchedule, Long> {

    @Query("""
            select ds from DoctorSchedule ds
            where ds.doctor.id= ?1
            and ds.date>= current date
            order by ds.date asc
            """)
    List<DoctorSchedule> findByDoctorId(long doctorId);

    @Query("select ds from DoctorSchedule ds where ds.doctor.id= ?1")
    List<DoctorSchedule> getSchedule(Long doctorId);

    List<DoctorSchedule> findByDoctorIdAndDate(Long doctorId, LocalDate date);
}
