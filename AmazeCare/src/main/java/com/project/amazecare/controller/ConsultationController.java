package com.project.amazecare.controller;

import com.project.amazecare.dto.ConsultReqDto;
import com.project.amazecare.service.ConsultationService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/consultation")
@AllArgsConstructor
public class ConsultationController {
    private final ConsultationService consultationService;

    // Access: doctor
    @GetMapping("/add")
    public ResponseEntity<?> addConsult(@RequestBody ConsultReqDto consultReqDto){
        consultationService.addConsult(consultReqDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
