package com.springboot.myapp.utility;

import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class PlanUtility {

    public LocalDate computeEndDate(LocalDate startDate, int days){
        return startDate.plusDays(days);
    }
}
