package com.project.amazecare.controller;

import com.project.amazecare.service.PrescriptionService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/prescription")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:5173/")
public class PrescriptionController {
    private final PrescriptionService prescriptionService;


}
