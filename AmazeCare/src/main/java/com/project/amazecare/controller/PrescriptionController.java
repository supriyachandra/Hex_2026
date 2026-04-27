package com.project.amazecare.controller;

import com.project.amazecare.dto.PrescribeDto;
import com.project.amazecare.repository.PrescriptionRepository;
import com.project.amazecare.service.PrescriptionService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/prescription")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:5173/")
public class PrescriptionController {
    private final PrescriptionService prescriptionService;


}
