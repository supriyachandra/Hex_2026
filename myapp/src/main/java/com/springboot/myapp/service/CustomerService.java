package com.springboot.myapp.service;

import com.springboot.myapp.dto.CustomerReqDto;
import com.springboot.myapp.exceptions.ResourceNotFoundException;
import com.springboot.myapp.mapper.CustomerMapper;
import com.springboot.myapp.model.Customer;
import com.springboot.myapp.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    public Customer insertCustomer(CustomerReqDto customerReqDto) {
        // convert to customer
        Customer customer= CustomerMapper.mapTo(customerReqDto);
        // extra mappings if required -- no
        // save in DB
        return customerRepository.save(customer);
    }

    public List<CustomerReqDto> getAllCustomers(int page, int size) {
        Pageable pageable= PageRequest.of(page, size);

        Page<Customer> customerPage= customerRepository.findAll(pageable);

        return customerPage
                .stream()
                .map(CustomerMapper :: mapToDto)
                .toList();
    }

    public CustomerReqDto getById(Long id) {
        Customer customer= customerRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Invalid ID")
                );
        return new CustomerReqDto(
                customer.getName(),
                customer.getEmail(),
                customer.getCity()
        );
    }
}
