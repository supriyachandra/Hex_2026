package com.project.amazecare.service;

import com.project.amazecare.dto.AdmissionReqDto;
import com.project.amazecare.enums.AdmissionStatus;
import com.project.amazecare.enums.PatientType;
import com.project.amazecare.exception.PatientAlreadyExistsException;
import com.project.amazecare.exception.ResourceNotFoundException;
import com.project.amazecare.mapper.AdmissionMapper;
import com.project.amazecare.model.Admission;
import com.project.amazecare.model.Doctor;
import com.project.amazecare.model.Patient;
import com.project.amazecare.repository.AdmissionRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.event.Level;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;

@Service
@AllArgsConstructor
@Slf4j
public class AdmissionService {
    private final AdmissionRepository admissionRepository;
    private final DoctorService doctorService;
    private final PatientService patientService;

    public void admitPatient(@Valid AdmissionReqDto admissionReqDto) {

        log.atLevel(Level.INFO).log("Called admitPatient: Admin admits the IPD patients");

        Admission admission= AdmissionMapper.mapToEntity(admissionReqDto);
        // DI patient and doctor by id
        Doctor doctor= doctorService.getById(admissionReqDto.doctor_id());
        Patient patient= patientService.getByPatientId(admissionReqDto.patient_id());

        if(admissionRepository.patientAlreadyAdmitted(patient.getId())>0){
            throw new PatientAlreadyExistsException("Patient already admitted");
        }
        patient.setPatientType(PatientType.IPD);
        patientService.savePatient(patient);

        admission.setDoctor(doctor);
        admission.setPatient(patient);
        admission.setAdmissionDate(LocalDate.now());
        admission.setStatus(AdmissionStatus.ADMITTED);

        admissionRepository.save(admission);
        log.atLevel(Level.INFO).log("Patient admitted!");
    }

    public Admission findAdmissionById(@NotNull long id) {
        log.atLevel(Level.INFO)
                .log("Called findAdmissionById: returns admissions by admission ID");

        return admissionRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Admission ID Invalid"));
    }

    public void dischargePatient(long admissionId) {
        // validation, if id exists
        Admission admission= admissionRepository.findById(admissionId)
                .orElseThrow(()-> new ResourceNotFoundException("Admission ID invalid!"));
        // using jpql
        admissionRepository.dischargePatient(admissionId);
    }
}
