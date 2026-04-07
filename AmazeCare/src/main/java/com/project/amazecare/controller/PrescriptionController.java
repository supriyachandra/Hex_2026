package com.project.amazecare.controller;

import com.project.amazecare.dto.PrescribeDto;
import com.project.amazecare.repository.PrescriptionRepository;
import com.project.amazecare.service.PrescriptionService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/prescription")
@AllArgsConstructor
public class PrescriptionController {
    private final PrescriptionService prescriptionService;

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> prescribeMedicine(@RequestBody PrescribeDto prescribeDto){
        prescriptionService.prescribeMedicine(prescribeDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
