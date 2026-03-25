package com.springboot.myapp.mapper;

import com.springboot.myapp.dto.CustomerReqDto;
import com.springboot.myapp.model.Customer;
import org.springframework.stereotype.Component;

public class CustomerMapper {

    public static Customer mapTo(CustomerReqDto customerReqDto) {
        Customer customer= new Customer();
        customer.setName(customerReqDto.name());
        customer.setEmail(customerReqDto.email());
        customer.setCity(customerReqDto.city());
        return customer;
    }

    public static CustomerReqDto mapToDto(Customer customer){
        return new CustomerReqDto(
                customer.getName(),
                customer.getEmail(),
                customer.getCity()
        );
    }
}
