package com.project.amazecare.mapper;

import com.project.amazecare.dto.*;
import com.project.amazecare.enums.PatientType;
import com.project.amazecare.model.Consultation;

import java.time.LocalDate;
import java.util.List;

public class ConsultationMapper {
    public static Consultation mapToEntity(ConsultReqDto consultReqDto){
        Consultation consultation= new Consultation();
        consultation.setDiagnosis(consultReqDto.diagnosis());
        consultation.setExamination(consultReqDto.examination());
        consultation.setTreatmentPlan(consultReqDto.treatmentPlan());
        consultation.setSymptomNotes(consultReqDto.symptomNotes());
        return consultation;
    }

    public static MedicalRecordDto mapToRecordDto(Consultation consultation) {

        List<ConsultationDto> consultationList =
                List.of(ConsultationMapper.mapToConsultationDto(consultation));

        List<PrescriptionDto> prescriptionList =
                consultation.getPrescriptions()
                        .stream()
                        .map(PrescriptionMapper::mapToDto)
                        .toList();

        List<TestDto> testList =
                consultation.getTests()
                        .stream()
                        .map(TestsMapper::mapToDto)
                        .toList();

        // ✅ OPD FIRST (safe check)
        if (consultation.getAppointment() != null) {

            return new MedicalRecordDto(
                    consultation.getAppointment().getId(),
                    PatientType.OPD,
                    consultation.getAppointment().getPatient().getName(),
                    consultation.getAppointment().getDoctor().getName(),
                    consultation.getAppointment().getAppointmentDate(),
                    consultationList,
                    prescriptionList,
                    testList
            );
        }

        // ✅ IPD fallback
        if (consultation.getAdmission() != null) {

            return new MedicalRecordDto(
                    consultation.getAdmission().getId(),
                    PatientType.IPD,
                    consultation.getAdmission().getPatient().getName(),
                    consultation.getAdmission().getDoctor().getName(),
                    consultation.getAdmission().getAdmissionDate(), //  FIXED (was LocalDate.now())
                    consultationList,
                    prescriptionList,
                    testList
            );
        }

        //  edge case safeguard
        throw new RuntimeException("Consultation has neither appointment nor admission");
    }

    public static ConsultationDto mapToConsultationDto(Consultation c){
        return new ConsultationDto(
                c.getExamination(),
                c.getDiagnosis(),
                c.getTreatmentPlan(),
                c.getSymptomNotes()
        );
    }

}
