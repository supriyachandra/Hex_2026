package com.springboot.myapp.controller;

import com.springboot.myapp.dto.CustomerReqDto;
import com.springboot.myapp.dto.CustomerRespDto;
import com.springboot.myapp.dto.CustomerSignUpDto;
import com.springboot.myapp.dto.TicketResPageDto;
import com.springboot.myapp.mapper.CustomerMapper;
import com.springboot.myapp.model.Customer;
import com.springboot.myapp.service.CustomerService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/customer")
@CrossOrigin(origins = "http://localhost:5173/")
public class CustomerController {

    private final CustomerService customerService;

//    @PostMapping("/add")
//    public Customer insertCustomer(@Valid @RequestBody CustomerReqDto customerReqDto){
//        return customerService.insertCustomer(customerReqDto);
//    }

    // Customer Sign Up -- access: anyone, permit all
    @PostMapping("/sign-up")
    public ResponseEntity<?> customerSignUp(@RequestBody CustomerSignUpDto customerSignUpDto){
        customerService.customerSignUp(customerSignUpDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
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

    @GetMapping("/get-one")
    public CustomerRespDto getCustomer(Principal principal){
        Customer customer= customerService.getByUsername(principal.getName());
        return CustomerMapper.mapToRespDto(customer);
    }

    //Add Pagination
    @GetMapping("/api/get-all")
    public List<CustomerRespDto> getAll(){
        return customerService.getAll();
    }

}
