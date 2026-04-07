package com.project.amazecare.mapper;

import com.project.amazecare.dto.ConsultReqDto;
import com.project.amazecare.model.Consultation;

public class ConsultationMapper {
    public static Consultation mapToEntity(ConsultReqDto consultReqDto){
        Consultation consultation= new Consultation();
        consultation.setDiagnosis(consultReqDto.diagnosis());
        consultation.setExamination(consultReqDto.examination());
        consultation.setTreatmentPlan(consultReqDto.treatmentPlan());
        consultation.setSymptomNotes(consultReqDto.symptomNotes());
        return consultation;
    }

}
