package com.project.amazecare.controller;

import com.project.amazecare.dto.*;
import com.project.amazecare.model.Patient;
import com.project.amazecare.service.PatientService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/patient")
public class PatientController {

    private final PatientService patientService;

    // add patient
    // access: permit all
    @PostMapping("/sign-up")
    public ResponseEntity<?> patientSignUp(@Valid @RequestBody PatientSignUpDto patientSignUpDto){
        patientService.patientSignUp(patientSignUpDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // create patient --- by ADMIN
    @PostMapping("/create")
    public ResponseEntity<?> createPatient(@Valid @RequestBody CreatePatientDto createPatientDto){
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
    public List<CreatePatientDto> getAllPatients(
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "size", required = false, defaultValue = "5") int size
    ){
        return patientService.getAllPatients(page, size);
    }
}

/*
Patient sign up --> dashboard -> view all doctors
--> see doctors by specialization
--> log in
-> Book Appointment (PENDING)
Wait for Doctor -->  Patient can Cancel/Reschedule
--> doctor confirms or rejects the appointment
...
- Consultation happens (COMPLETED)
- View Medical History
*/