package com.project.amazecare.mapper;

import com.project.amazecare.dto.PrescribeDto;
import com.project.amazecare.dto.PrescriptionDto;
import com.project.amazecare.model.Prescription;

public class PrescriptionMapper {
    public static Prescription mapToEntity(PrescribeDto prescribeDto){
        Prescription prescription= new Prescription();
        prescription.setMedicineName(prescribeDto.medicine_name());
        prescription.setDosage(prescribeDto.dosage());
        return prescription;
    }

    public static PrescriptionDto mapToDto(Prescription p){
        return new PrescriptionDto(
                p.getMedicineName(),
                p.getDosage()
        );
    }

    public static Prescription mapToEntity2(PrescriptionDto p) {
        Prescription prescription= new Prescription();
        prescription.setMedicineName(p.medicine_name());
        prescription.setDosage(p.dosage());
        return prescription;
    }
}
