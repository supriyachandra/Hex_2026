package com.project.amazecare.controller;

import com.project.amazecare.model.Specialization;
import com.project.amazecare.service.SpecializationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/specialization")
public class SpecializationController {
    private final SpecializationService specializationService;

    // Access: permit all--- for dropdowns
    @GetMapping("/get-all")
    public List<Specialization> getAllSpecialization(){
        return specializationService.getAllSpecialization();
    }
}
