package com.springboot.myapp.controller;

import com.springboot.myapp.dto.*;
import com.springboot.myapp.model.Ticket;
import com.springboot.myapp.service.TicketService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/ticket")
public class TicketController {

    private final TicketService ticketService;

    @PostMapping("/add")
    public ResponseEntity<?> insertTicket(@Valid @RequestBody TicketDto ticketReqDto, Principal principal){
        String username= principal.getName();
        ticketService.insertTicket(ticketReqDto, username);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/get-all")
    public TicketResPageDto getAllTickets(
            @RequestParam(value= "page", defaultValue = "0", required = false) int page,
            @RequestParam(value = "size", defaultValue = "5", required = false) int size){
        return ticketService.getAllTickets(page, size);
    }

    @GetMapping("/get/{id}")
    public TicketRespDto getById(@PathVariable long id){
        return ticketService.getById(id);
    }

    @PostMapping("/get/filter")
    public List<TicketRespDto> getByFilter(@RequestBody FilterReqDto filterReqDto){
        return ticketService.getByFilter(filterReqDto);
    }

    @PutMapping("/add/{ticketId}/{executiveId}")
    public ResponseEntity<?> addExecutiveToTicket(@PathVariable long ticketId, @PathVariable long executiveId){
        ticketService.addExecutiveToTicket(ticketId, executiveId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

//    // GET TICKETS BY CUSTOMER ID (TO BE DELETED)
//    @GetMapping("/get-ticket/{customerId}")
//    public List<TicketCustomerDto> getTicketByCustomerId(@PathVariable long customerId){
//        return ticketService.getTicketByCustomerId(customerId);
//    }

    // get ticket by customer username (token)
    @GetMapping("/get-tickets")
    public List<TicketCustomerDto> getTicketsByCustomer(Principal principal){
        String username= principal.getName();
        return ticketService.getTicketsByUsername(username);
    }
}
