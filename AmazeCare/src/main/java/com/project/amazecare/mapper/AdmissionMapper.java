package com.project.amazecare.mapper;

import com.project.amazecare.dto.AdminRespDto;
import com.project.amazecare.dto.AdmissionReqDto;
import com.project.amazecare.dto.AdmitRespDto;
import com.project.amazecare.enums.AdmissionStatus;
import com.project.amazecare.enums.PatientType;
import com.project.amazecare.model.Admission;

import java.time.LocalDate;

public class AdmissionMapper {

    public static Admission mapToEntity(AdmissionReqDto admissionReqDto){
        Admission admission= new Admission();
        admission.setReason(admissionReqDto.reason());
        admission.setBedNumber(admissionReqDto.bedNumber());
        admission.setRoomNumber(admissionReqDto.roomNumber());

        return admission;
    }

    public static AdmitRespDto mapToRespDto(Admission a){
        return new AdmitRespDto(
                a.getId(),
                a.getPatient().getId(),
                a.getPatient().getName(),
                a.getDoctor().getId(),
                a.getDoctor().getSpecialization().getName(),
                a.getDoctor().getName(),
                a.getReason(),
                a.getRoomNumber(),
                a.getBedNumber(),
                a.getAdmissionDate(),
                a.getDischargeDate(),
                a.getStatus(),
                a.getPatientType()
        );
    }
}