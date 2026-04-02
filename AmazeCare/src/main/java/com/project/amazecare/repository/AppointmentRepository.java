package com.project.amazecare.repository;

import com.project.amazecare.model.Appointment;
import com.project.amazecare.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    @Query("""
        select a from Appointment a""")
    List<Appointment> viewAppointments();

    @Query("select p from Patient p where p.user.username=?1")
    Patient getByUsername(String username);
}
