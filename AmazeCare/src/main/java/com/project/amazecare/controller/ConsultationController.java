package com.project.amazecare.controller;

import com.project.amazecare.dto.ConsultReqDto;
import com.project.amazecare.dto.MedicalRecordDto;
import com.project.amazecare.dto.MedicalRecordPagination;
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
@CrossOrigin(origins = "http://localhost:5173/")
public class ConsultationController {
    private final ConsultationService consultationService;
    private final PatientService patientService;

    // Access: doctor
    @PostMapping("/add")
    public ResponseEntity<HttpStatus> addConsult(@RequestBody ConsultReqDto consultReqDto,
                                        Principal principal){
        consultationService.addConsult(consultReqDto, principal);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    //doctor
    @GetMapping("/consult-history")
    public MedicalRecordPagination getMedicalRecord(@RequestParam(name = "page", defaultValue = "0", required = false) int page,
                                                    @RequestParam(name = "size", defaultValue = "5", required = false) int size,
                                                    @RequestParam String type,
                                                    Principal principal){

        return consultationService.getConsultHistory(page, size, type, principal.getName());
    }

    @GetMapping("/appointment/{appId}")
    public MedicalRecordDto getConsultByAppId(@PathVariable Long appId, Principal principal){

        return  consultationService.getConsultByAppId(appId, principal.getName());
    }

}
