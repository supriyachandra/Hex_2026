package com.project.amazecare.mapper;

import com.project.amazecare.dto.DoctorReqDto;
import com.project.amazecare.dto.DoctorRespDto;
import com.project.amazecare.dto.DoctorSignUpDto;
import com.project.amazecare.model.Doctor;

import javax.print.Doc;

public class DoctorMapper {

    public static Doctor mapTo(DoctorReqDto doctorReqDto) {
        Doctor doctor= new Doctor();
        doctor.setName(doctorReqDto.name());
        doctor.setEmail(doctorReqDto.email());
        doctor.setPhone(doctorReqDto.phone());
        doctor.setDesignation(doctorReqDto.designation());
        doctor.setExperience(doctorReqDto.experience());
        doctor.setQualification(doctorReqDto.qualification());
        return doctor;
    }

    public static DoctorReqDto mapToDto(Doctor doctor){
        return new DoctorReqDto(
                doctor.getName(),
                doctor.getExperience(),
                doctor.getPhone(),
                doctor.getEmail(),
                doctor.getQualification(),
                doctor.getDesignation(),
                doctor.getSpecialization().getName()
        );
    }

    public static Doctor mapFromSignUpDto(DoctorSignUpDto doctorSignUpDto){
        Doctor doctor= new Doctor();
        doctor.setName(doctorSignUpDto.name());
        doctor.setEmail(doctorSignUpDto.email());
        doctor.setPhone(doctorSignUpDto.phone());
        doctor.setDesignation(doctorSignUpDto.designation());
        doctor.setExperience(doctorSignUpDto.experience());
        doctor.setQualification(doctorSignUpDto.qualification());
        return doctor;
    }

    public static DoctorRespDto mapToRespDto(Doctor doctor){
        return new DoctorRespDto(
                doctor.getId(),
                doctor.getName(),
                doctor.getExperience(),
                doctor.getEmail(),
                doctor.getQualification(),
                doctor.getDesignation(),
                doctor.getSpecialization().getName()
        );
    }
}
