package com.springboot.myapp.repository;

import com.springboot.myapp.dto.CustomerReqDto;
import com.springboot.myapp.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query("select c from Customer c where c.user.username=?1")
    Customer getByUsername(String username);
}
