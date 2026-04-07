package com.project.amazecare.mapper;

import com.project.amazecare.dto.PrescribeDto;
import com.project.amazecare.model.Prescription;

public class PrescriptionMapper {
    public static Prescription mapToEntity(PrescribeDto prescribeDto){
        Prescription prescription= new Prescription();
        prescription.setMedicineName(prescribeDto.medicine_name());
        prescription.setDosage(prescribeDto.dosage());
        return prescription;
    }
}
