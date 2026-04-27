package com.springboot.myapp.service;

import com.springboot.myapp.dto.PlanRespDto;
import com.springboot.myapp.exceptions.ResourceNotFoundException;
import com.springboot.myapp.mapper.PlanMapper;
import com.springboot.myapp.model.Plan;
import com.springboot.myapp.repository.PlanRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PlanService {

    private final PlanRepository planRepository;

    public Plan findById(long id){
        return planRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Plan Id Invalid!")
                );
    }

    public List<PlanRespDto> getAllPlans() {
        List<Plan> planList= planRepository.findAll();
        return planList.stream()
                .map(PlanMapper:: mapToDto)
                .toList();
    }
}
