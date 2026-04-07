package com.project.amazecare.controller;

import com.project.amazecare.dto.ConsultReqDto;
import com.project.amazecare.dto.MedicalRecordDto;
import com.project.amazecare.service.ConsultationService;
import com.project.amazecare.service.PatientService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/consultation")
@AllArgsConstructor
public class ConsultationController {
    private final ConsultationService consultationService;
    private final PatientService patientService;

    // Access: doctor
    @PostMapping("/add")
    public ResponseEntity<?> addConsult(@RequestBody ConsultReqDto consultReqDto,
                                        Principal principal){
        consultationService.addConsult(consultReqDto, principal);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // show medical records of a patient--- to patient so use principal

    /*
    @GetMapping("/api/medical-record")
    public List<MedicalRecordDto> showMedicalRecord(Principal principal){
        return consultationService.showMedicalRecord(principal);
    }
     */

}
