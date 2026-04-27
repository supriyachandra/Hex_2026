package com.springboot.myapp.repository;

import com.springboot.myapp.dto.CustomerByPlanDto;
import com.springboot.myapp.model.CustomerPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CustomerPlanRepository extends JpaRepository<CustomerPlan, Long> {
    @Query("""
           select c.id, c.name, c.email, c.city,
           cp.start_date, cp.end_date, p.planName
           from CustomerPlan cp join cp.customer c
           join cp.plan p where cp.plan.id= ?1
           """)
    public List<CustomerByPlanDto> getCustomersByPlan(long planId);

    @Query("""
        select cp from CustomerPlan cp where cp.customer.user.username=?1
        and current_date < cp.end_date
        """)
    List<CustomerPlan> getActivePlans(String username);
}
