package com.project.amazecare.controller;

import com.project.amazecare.dto.AdmissionReqDto;
import com.project.amazecare.dto.AdmissionsPagination;
import com.project.amazecare.service.AdmissionService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/admission")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:5173/")
public class AdmissionController {

    final private AdmissionService admissionService;

    @PostMapping("/admit")
    public ResponseEntity<HttpStatus> admitPatient(@Valid @RequestBody AdmissionReqDto admissionReqDto){
        admissionService.admitPatient(admissionReqDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // GET active all patients admitted
    @GetMapping("/get-all-active")
    public AdmissionsPagination getAllActive(
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "size", required = false, defaultValue = "10") int size){
        return admissionService.getAllActive(page, size);
    }

    // GET active all patients admitted-- doctor
    @GetMapping("/get-all-active/doc")
    public AdmissionsPagination getAllActiveDoc(
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "size", required = false, defaultValue = "10") int size,
            Principal principal){
        return admissionService.getAllActiveDoc(page, size, principal.getName());
    }

    // GET past ipd
    @GetMapping("/get-all-past")
    public AdmissionsPagination getAllPast(
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "size", required = false, defaultValue = "10") int size){
        return admissionService.getAllPast(page, size);
    }

    // request discharge--- doctor
    @PutMapping("/request-discharge/{admission_id}")
    public ResponseEntity<HttpStatus> requestDischarge(@PathVariable long admission_id){
        admissionService.requestDischarge(admission_id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    // GET past ipd --doctor
    @GetMapping("/get-all-past/doc")
    public AdmissionsPagination getAllPastDoctor(
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "size", required = false, defaultValue = "10") int size,
            Principal principal){
        return admissionService.getAllPastDoc(page, size, principal.getName());
    }

    // discharge patient---- by admin
    @PutMapping("/discharge/{admission_id}")
    public ResponseEntity<HttpStatus> dischargePatient(@PathVariable long admission_id){
        admissionService.dischargePatient(admission_id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/currently-admitted")
    public Long currentlyAdmitted(){
        return admissionService.currentlyAdmittedCount();
    }
}
