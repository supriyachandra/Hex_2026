package com.springboot.myapp.mapper;

import com.springboot.myapp.dto.CustomerReqDto;
import com.springboot.myapp.dto.CustomerSignUpDto;
import com.springboot.myapp.model.Customer;

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

    public static Customer mapSignUpDtoToEntity(CustomerSignUpDto customerSignUpDto){
        Customer customer= new Customer();
        customer.setName(customerSignUpDto.name());
        customer.setEmail(customerSignUpDto.email());
        customer.setCity(customerSignUpDto.city());
        return customer;
    }
}
