package com.project.amazecare.controller;

import com.project.amazecare.model.Specialization;
import com.project.amazecare.service.SpecializationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/specialization")
@CrossOrigin(origins = "http://localhost:5173/")
public class SpecializationController {
    private final SpecializationService specializationService;

    // Access: permit all--- for dropdowns
    @GetMapping("/get-all")
    public List<Specialization> getAllSpecialization(){
        return specializationService.getAllSpecialization();
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> add(@RequestParam String specialization){
        specializationService.add(specialization);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
