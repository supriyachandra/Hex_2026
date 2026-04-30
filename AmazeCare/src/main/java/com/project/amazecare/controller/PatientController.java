package com.project.amazecare.controller;

import com.project.amazecare.dto.*;
import com.project.amazecare.service.ConsultationService;
import com.project.amazecare.service.PatientService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/patient")
@CrossOrigin(origins = "http://localhost:5173/")
public class PatientController {

    private final PatientService patientService;
    private final ConsultationService consultationService;

    // add patient
    // access: permit all
    @PostMapping("/sign-up")
    public ResponseEntity<HttpStatus> patientSignUp(@Valid @RequestBody PatientSignUpDto patientSignUpDto){
        patientService.patientSignUp(patientSignUpDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // create patient --- by ADMIN
    @PostMapping("/create")
    public ResponseEntity<HttpStatus> createPatient(@Valid @RequestBody CreatePatientDto createPatientDto){
        patientService.createPatient(createPatientDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // Get: patient data (Profile page)
    // access: doctor
    @GetMapping("/get/{id}")
    public PatientReqDto getById(@PathVariable long id){
        return patientService.getById(id);
    }

    // by admin
    @GetMapping("/get-all")
    public PatientPaginationDto getAllPatients(
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "size", required = false, defaultValue = "5") int size
    ){
        return patientService.getAllPatients(page, size);
    }

    @GetMapping("/get-one")
    public PatientRespDto getPatient(Principal principal){
        return patientService.getPatient(principal.getName());
    }

    @GetMapping("/medical-history")
    public List<MedicalRecordDto> getMedicalRecord(Principal principal){
        return consultationService.getMedicalRecord(principal.getName());
    }


    @GetMapping("/all")
    public List<PatientRespDto> All(){
        return patientService.all();
    }

    @GetMapping("/count-patient")
    public Long totalPatients(){
        return patientService.totalPatient();
    }
}