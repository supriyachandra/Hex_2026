package com.project.amazecare.model;

import com.project.amazecare.enums.AppointmentStatus;
import com.project.amazecare.enums.VisitType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CurrentTimestamp;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name= "appointment_date")
    private LocalDate appointmentDate;

    @Column(name = "time_slot")
    private LocalTime timeSlot;

    private String symptoms;

    @Column(name= "visit_type")
    @Enumerated(EnumType.STRING)
    private VisitType visitType;

    @Enumerated(EnumType.STRING)
    private AppointmentStatus appointmentStatus;

    @CurrentTimestamp
    @Column(name = "created_at")
    private Instant createdAt;

    @ManyToOne
    private Doctor doctor;
    // 1 doctor- M appointments

    @ManyToOne
    private Patient patient;
    // 1 patient- M appointments
}
