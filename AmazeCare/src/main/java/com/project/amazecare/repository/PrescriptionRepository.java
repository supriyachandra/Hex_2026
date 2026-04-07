package com.project.amazecare.repository;

import com.project.amazecare.dto.TimeSlotsDto;
import com.project.amazecare.model.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {

    @Query("""
            select ds.startTime from DoctorSchedule ds where
            ds.doctor.id= ?1 and
            ds.date=?2
            """)
    List<TimeSlotsDto> getTimeSlots(long doctorId, LocalDate date);
}
