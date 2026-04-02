package com.project.amazecare.controller;

import com.project.amazecare.dto.AppointmentDto;
import com.project.amazecare.dto.DoctorReqDto;
import com.project.amazecare.dto.PatientReqDto;
import com.project.amazecare.dto.PatientSignUpDto;
import com.project.amazecare.model.Patient;
import com.project.amazecare.service.PatientService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    // Get: patient data (Profile page)
    // access: doctor
    @GetMapping("/get/{id}")
        public PatientReqDto getById(@PathVariable long id){
            return patientService.getById(id);
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