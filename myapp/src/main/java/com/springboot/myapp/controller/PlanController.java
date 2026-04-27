package com.springboot.myapp.controller;

import com.springboot.myapp.dto.PlanRespDto;
import com.springboot.myapp.service.PlanService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/plan")
@CrossOrigin(origins = "http://localhost:5173/")
public class PlanController {

    private final PlanService planService;

    @GetMapping("/get-all")
    public List<PlanRespDto> getAllPlans(){
        return planService.getAllPlans();
    }
}
