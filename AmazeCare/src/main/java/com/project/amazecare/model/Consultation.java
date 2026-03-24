package com.project.amazecare.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    // - appointment_id (FK)
}
