package com.springboot.myapp.controller;

import com.springboot.myapp.dto.CustomerReqDto;
import com.springboot.myapp.dto.TicketResPageDto;
import com.springboot.myapp.model.Customer;
import com.springboot.myapp.service.CustomerService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/customer")
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping("/add")
    public Customer insertCustomer(@Valid @RequestBody CustomerReqDto customerReqDto){
        return customerService.insertCustomer(customerReqDto);
    }

    @GetMapping("/get-all")
    public List<CustomerReqDto> getAllCustomers(
            @RequestParam(value= "page", defaultValue = "0", required = false) int page,
            @RequestParam(value = "size", defaultValue = "5", required = false) int size){
        return customerService.getAllCustomers(page, size);
    }

    @GetMapping("/get/id/{id}")
    public CustomerReqDto getById(@PathVariable Long id){
        return customerService.getById(id);
    }


}
