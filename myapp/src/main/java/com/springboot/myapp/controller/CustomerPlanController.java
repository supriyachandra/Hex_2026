package com.springboot.myapp.controller;

import com.springboot.myapp.dto.CustomerByPlanDto;
import com.springboot.myapp.dto.CustomerPlanReqDto;
import com.springboot.myapp.service.CustomerPlanService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/customer/plan")
public class CustomerPlanController {

    private final CustomerPlanService customerPlanService;

    // access : admin
    @PostMapping("/assign-plan/{customerId}/{planId}")
    public ResponseEntity<?> assignPlanToCustomer(@PathVariable long customerId,
                                     @PathVariable long planId,
                                     @RequestBody CustomerPlanReqDto customerPlanReqDto){
        customerPlanService.assignPlanToCustomer(customerId, planId, customerPlanReqDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // access: Customer buys plan
    @PostMapping("/buy-plan/{planId}")
    public ResponseEntity<?> customerBuyPlan(Principal principal,
                                             @PathVariable long planId,
                                             @RequestBody CustomerPlanReqDto customerPlanReqDto){
        String username= principal.getName();
        customerPlanService.customerBuyPlan(username, planId, customerPlanReqDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    // list of customers based on Plan id
    // custid, name, email, city, start date, end date, plan name
    @GetMapping("/get-customers/{planId}")
    public List<CustomerByPlanDto> getCustomersByPlan(@PathVariable long planId){
        return customerPlanService.getCustomersByPlan(planId);
    }
}
