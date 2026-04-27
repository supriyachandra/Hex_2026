package com.project.amazecare.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Consultation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String examination;     // vitals + observations..
    private String diagnosis;

    @Column(name = "treatment_plan")
    private String treatmentPlan;

    @Column(name = "symptom_notes")
    private String symptomNotes;

    @OneToOne
    private Appointment appointment;
    // 1 Appointment: 1 Consult

    @ManyToOne
    private Admission admission;
    // for admitted patient

    @OneToMany(mappedBy = "consultation", cascade = CascadeType.ALL)
    private List<Prescription> prescriptions;

    @OneToMany(mappedBy = "consultation", cascade = CascadeType.ALL)
    private List<RecommendedTests> tests;
}
