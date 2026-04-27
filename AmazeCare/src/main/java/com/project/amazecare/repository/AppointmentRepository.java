package com.project.amazecare.repository;

import com.project.amazecare.dto.AppointmentRespDto;
import com.project.amazecare.enums.AppointmentStatus;
import com.project.amazecare.model.Appointment;
import com.project.amazecare.model.Patient;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import java.time.LocalDate;
import java.time.LocalTime;
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

    @Query("""
    select count(a.id) from Appointment a where a.patient.user.username=?1
    and a.appointmentDate >= current_date
    """)
    int countUpcomingByUsername(String username);

    @Query("""
        select a from Appointment a where a.patient.user.username=?1
                and a.appointmentDate >= current_date
        """)
    List<Appointment> getUpcomingAppointments(String username);

    @Query("select a from Appointment a where a.patient.user.username= ?1 order by a.appointmentDate desc")
    Page<Appointment> getAppointments(String username, Pageable pageable);

    @Query("""
        select a from Appointment a where a.doctor.user.username=?1
        and a.appointmentDate >= current_date
                order by a.appointmentDate asc
        """)
    List<Appointment> getUpcomingAppointmentsDoctor(String doctorUsername);

    @Query("""
    select a from Appointment a where a.doctor.user.username=?1
    and a.appointmentDate = current_date
    and a.appointmentStatus = ?2
    """)
    List<Appointment> todayConfirm(String dUsername, AppointmentStatus status);

    @Modifying
    @Transactional
    @Query("update Appointment a set a.appointmentStatus=?3 where a.id=?1 and a.patient.user.username=?2")
    void cancelApp(Long id, String username, AppointmentStatus status);

    @Query("""
    select a from Appointment a where a.appointmentDate >= current_date
        order by a.appointmentDate desc
    """)
    Page<Appointment> getAllUpcoming(Pageable pageable);

    @Query("""
    select new com.project.amazecare.dto.AppointmentRespDto(a.id,
        a.appointmentDate, a.timeSlot, a.patient.id, a.patient.name,
        a.doctor.id, a.doctor.specialization.name, a.doctor.name,
        a.appointmentStatus, a.symptoms)
    from Appointment a
    where a.appointmentDate= current_date
    """)
    List<AppointmentRespDto> allToday();
}
