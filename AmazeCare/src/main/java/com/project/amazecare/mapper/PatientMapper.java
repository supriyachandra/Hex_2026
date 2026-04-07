package com.project.amazecare.mapper;

import com.project.amazecare.dto.CreatePatientDto;
import com.project.amazecare.dto.PatientReqDto;
import com.project.amazecare.dto.PatientSignUpDto;
import com.project.amazecare.model.Patient;
import org.slf4j.helpers.CheckReturnValue;

public class PatientMapper {

    public static Patient mapTo(PatientReqDto patientReqDto){
        Patient patient= new Patient();
        patient.setName(patientReqDto.name());
        patient.setGender(patientReqDto.gender());
        patient.setDateOfBirth(patientReqDto.DOB());
        patient.setPhone(patientReqDto.phone());
        return patient;
    }

    public static PatientReqDto mapToDto(Patient patient) {
        return new PatientReqDto(
                patient.getName(),
                patient.getDateOfBirth(),
                patient.getGender(),
                patient.getPhone()
        );
    }

    public static Patient mapFromSignUpDto(PatientSignUpDto patientSignUpDto){
        Patient patient= new Patient();
        patient.setName(patientSignUpDto.name());
        patient.setGender(patientSignUpDto.gender());
        patient.setDateOfBirth(patientSignUpDto.DOB());
        patient.setPhone(patientSignUpDto.phone());
        return patient;
    }

    public static Patient mapFromCreatePatient(CreatePatientDto createPatientDto){
        Patient patient= new Patient();
        patient.setName(createPatientDto.name());
        patient.setGender(createPatientDto.gender());
        patient.setDateOfBirth(createPatientDto.DOB());
        patient.setPhone(createPatientDto.phone());
        patient.setPatientType(createPatientDto.patientType());
        return patient;
    }

    public static CreatePatientDto mapToCreatePatientDto(Patient patient){
        return new CreatePatientDto(
                patient.getName(),
                patient.getDateOfBirth(),
                patient.getGender(),
                patient.getPhone(),
                patient.getPatientType()
        );
    }
}
