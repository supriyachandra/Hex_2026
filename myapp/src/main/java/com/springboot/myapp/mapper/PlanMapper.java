package com.springboot.myapp.mapper;

import com.springboot.myapp.dto.PlanRespDto;
import com.springboot.myapp.model.Plan;

public class PlanMapper {

    public static PlanRespDto mapToDto(Plan plan){
        return new PlanRespDto(
                plan.getPlanName(),
                plan.getPrice(),
                plan.getDays()
        );
    }
}
