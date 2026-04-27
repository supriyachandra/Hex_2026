package com.springboot.myapp.mapper;

import com.springboot.myapp.dto.ActivePlanDto;
import com.springboot.myapp.model.Customer;
import com.springboot.myapp.model.CustomerPlan;

public class CustomerPlanMapper {

    public static ActivePlanDto mapToActiveDto(CustomerPlan customerPlan){
        return new ActivePlanDto(
                customerPlan.getPlan().getPlanName(),
                customerPlan.getPlan().getPrice(),
                customerPlan.getPlan().getDays(),
                customerPlan.getStart_date(),
                customerPlan.getEnd_date()
        );
    }
}
