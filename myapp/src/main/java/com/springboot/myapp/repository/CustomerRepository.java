package com.springboot.myapp.repository;

import com.springboot.myapp.dto.CustomerReqDto;
import com.springboot.myapp.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
