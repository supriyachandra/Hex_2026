package com.springboot.myapp.service;

import com.springboot.myapp.dto.ActivePlanDto;
import com.springboot.myapp.dto.CustomerByPlanDto;
import com.springboot.myapp.dto.CustomerPlanReqDto;
import com.springboot.myapp.mapper.CustomerPlanMapper;
import com.springboot.myapp.model.Customer;
import com.springboot.myapp.model.CustomerPlan;
import com.springboot.myapp.model.Plan;
import com.springboot.myapp.repository.CustomerPlanRepository;
import com.springboot.myapp.repository.CustomerRepository;
import com.springboot.myapp.utility.PlanUtility;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class CustomerPlanService {
    private final CustomerService customerService;
    private final PlanService planService;
    private final PlanUtility planUtility;
    private final CustomerPlanRepository customerPlanRepository;

    public List<CustomerByPlanDto> getCustomersByPlan(long planId) {
        // plan object -- id validation
        planService.findById(planId);
        return customerPlanRepository.getCustomersByPlan(planId);
    }

    public void assignPlanToCustomer(long customerId, long planId, CustomerPlanReqDto customerPlanReqDto) {
        // check plan id exists
        Plan plan= planService.findById(planId);

        // validate customer id
        Customer customer= customerService.getCustById(customerId);

        // create Customer plan object
        CustomerPlan customerPlan= new CustomerPlan();

        customerPlan.setStart_date(customerPlanReqDto.start_date());
        customerPlan.setDiscount(customerPlanReqDto.discount());
        customerPlan.setCoupon(customerPlanReqDto.coupon());
        customerPlan.setCustomer(customer);
        customerPlan.setPlan(plan);

        // Additional info in CustomerPlanDTo
        // end date
        LocalDate endDate= planUtility.computeEndDate(customerPlanReqDto.start_date(), plan.getDays());
        customerPlan.setEnd_date(endDate);

        // save
        customerPlanRepository.save(customerPlan);
    }

    public void customerBuyPlan(String username, long planId, CustomerPlanReqDto customerPlanReqDto) {
        // plan
        Plan plan= planService.findById(planId);

        // get customer by username
        Customer customer= customerService.getByUsername(username);

        // create Customer plan object
        CustomerPlan customerPlan= new CustomerPlan();

        customerPlan.setStart_date(customerPlanReqDto.start_date());
        customerPlan.setDiscount(customerPlanReqDto.discount());
        customerPlan.setCoupon(customerPlanReqDto.coupon());
        customerPlan.setCustomer(customer);
        customerPlan.setPlan(plan);

        // Additional info in CustomerPlanDTo
        // end date
        LocalDate endDate= planUtility.computeEndDate(customerPlanReqDto.start_date(), plan.getDays());
        customerPlan.setEnd_date(endDate);

        // save
        customerPlanRepository.save(customerPlan);
    }

    public List<ActivePlanDto> getActivePlan(String username) {
        List<CustomerPlan> customerPlanList= customerPlanRepository.getActivePlans(username);
        return customerPlanList.stream()
                .map(CustomerPlanMapper :: mapToActiveDto)
                .toList();
    }
}
