package com.project.amazecare.service;

import com.project.amazecare.dto.AdmissionReqDto;
import com.project.amazecare.dto.AdmissionsPagination;
import com.project.amazecare.dto.AdmitRespDto;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

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
        patientService.savePatient(patient);

        admission.setDoctor(doctor);
        admission.setPatient(patient);
        //admission.setPatientType(PatientType.IPD);
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


    public AdmissionsPagination getAllActive(int page, int size) {
        Pageable pageable= PageRequest.of(page, size);
        Page<Admission> admissionPage= admissionRepository.getAllActive(pageable);

        List<AdmitRespDto> admitRespDtos= admissionPage
                .stream()
                .map(AdmissionMapper:: mapToRespDto)
                .toList();

        long records= admissionPage.getTotalElements();
        int pages= admissionPage.getTotalPages();

        return new AdmissionsPagination(
                admitRespDtos,
                records,
                pages
        );
    }

    public void requestDischarge(long admissionId) {
        admissionRepository.requestDischarge(admissionId);
    }

    public AdmissionsPagination getAllPast(int page, int size) {
        Pageable pageable= PageRequest.of(page, size);
        Page<Admission> admissionPage= admissionRepository.getAllPast(pageable);

        List<AdmitRespDto> admitRespDtos= admissionPage
                .stream()
                .map(AdmissionMapper:: mapToRespDto)
                .toList();

        long records= admissionPage.getTotalElements();
        int pages= admissionPage.getTotalPages();

        return new AdmissionsPagination(
                admitRespDtos,
                records,
                pages
        );
    }

    public AdmissionsPagination getAllPastDoc(int page, int size, String username) {
        Pageable pageable= PageRequest.of(page, size);
        AdmissionStatus status= AdmissionStatus.valueOf("DISCHARGED");
        Page<Admission> admissionPage= admissionRepository.getAllPastDoc(pageable, username, status);

        List<AdmitRespDto> admitRespDtos= admissionPage
                .stream()
                .map(AdmissionMapper:: mapToRespDto)
                .toList();

        long records= admissionPage.getTotalElements();
        int pages= admissionPage.getTotalPages();

        return new AdmissionsPagination(
                admitRespDtos,
                records,
                pages
        );
    }

    public AdmissionsPagination getAllActiveDoc(int page, int size, String username) {
        Pageable pageable= PageRequest.of(page, size);
        Page<Admission> admissionPage= admissionRepository.getAllActiveDoc(pageable, username);

        List<AdmitRespDto> admitRespDtos= admissionPage
                .stream()
                .map(AdmissionMapper:: mapToRespDto)
                .toList();

        long records= admissionPage.getTotalElements();
        int pages= admissionPage.getTotalPages();

        return new AdmissionsPagination(
                admitRespDtos,
                records,
                pages
        );
    }

    public Long currentlyAdmittedCount() {
        return admissionRepository.countCurrentlyAdmitted();
    }
}
