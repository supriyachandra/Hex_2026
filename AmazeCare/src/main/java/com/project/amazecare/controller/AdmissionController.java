package com.project.amazecare.controller;

import com.project.amazecare.dto.AdmissionReqDto;
import com.project.amazecare.service.AdmissionService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admission")
@AllArgsConstructor
public class AdmissionController {

    final private AdmissionService admissionService;

    @PostMapping("/admit")
    public ResponseEntity<?> admitPatient(@Valid @RequestBody AdmissionReqDto admissionReqDto){
        admissionService.admitPatient(admissionReqDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // GET view all patients admitted (left to do)

    // discharge patient---- by admin
    @PutMapping("/discharge/{admission_id}")
    public ResponseEntity<?> dischargePatient(@PathVariable long admission_id){
        admissionService.dischargePatient(admission_id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
