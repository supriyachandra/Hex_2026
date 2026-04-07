package com.project.amazecare.mapper;

import com.project.amazecare.dto.AdmissionReqDto;
import com.project.amazecare.model.Admission;

public class AdmissionMapper {

    public static Admission mapToEntity(AdmissionReqDto admissionReqDto){
        Admission admission= new Admission();
        admission.setReason(admissionReqDto.reason());
        admission.setBedNumber(admissionReqDto.bedNumber());
        admission.setRoomNumber(admissionReqDto.roomNumber());

        return admission;
    }
}
