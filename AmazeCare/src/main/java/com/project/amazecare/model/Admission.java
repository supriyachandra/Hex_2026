package com.project.amazecare.model;

import com.project.amazecare.enums.AdmissionStatus;
import com.project.amazecare.enums.PatientType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Admission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Patient patient;

    @ManyToOne
    private Doctor doctor;

    @Column(name = "admission_date")
    private LocalDate admissionDate;

    @Column(name = "discharge_date")
    private LocalDate dischargeDate;

    @Column(name="room_number")
    private String roomNumber;

    @Column(name = "bed_number")
    private String bedNumber;

    private String reason;

    @Enumerated(EnumType.STRING)
    private AdmissionStatus status;

    @CreationTimestamp
    private Instant createdAt;

    @Enumerated(EnumType.STRING)
    private PatientType patientType= PatientType.IPD;
}
