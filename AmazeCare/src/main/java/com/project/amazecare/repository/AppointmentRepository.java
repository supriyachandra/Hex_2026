package com.project.amazecare.repository;

import com.project.amazecare.enums.AppointmentStatus;
import com.project.amazecare.model.Appointment;
import com.project.amazecare.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    @Query("""
        select a from Appointment a""")
    List<Appointment> viewAppointments();

    @Query("select p from Patient p where p.user.username=?1")
    Patient getByUsername(String username);

    @Query("""
    select a from Appointment a where
    (a.appointmentStatus=?1 or ?1 is null)
    and (?2 is null or lower(a.patient.name) like lower(concat('%', ?2, '%'))) and
    (a.appointmentDate=?3 or ?3 is null) and
    a.doctor.id= ?4
    """)
    List<Appointment> getAppointmentsWithFilter(AppointmentStatus status, String name, LocalDate date, long doctorId);
}
