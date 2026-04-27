package com.springboot.myapp.controller;

import com.springboot.myapp.dto.*;
import com.springboot.myapp.enums.TicketStatus;
import com.springboot.myapp.model.Ticket;
import com.springboot.myapp.service.TicketService;
import jakarta.annotation.Nonnull;
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
@CrossOrigin(origins = "http://localhost:5173/")
public class TicketController {

    private final TicketService ticketService;

    @PostMapping("/add")
    public ResponseEntity<?> insertTicket(@Valid @RequestBody TicketDto ticketReqDto, @Nonnull Principal principal){
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


    // for practicing pagination, validation
    //send list of tickets with customer id given where status is given as path variable

    @GetMapping("/get-all/{status}")
    public TicketByCustomerDto getTicketsByCustomerAndStatus(@PathVariable TicketStatus status,
                                                             Principal principal,
                                                             @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                                             @RequestParam(value = "size", required = false, defaultValue = "5") int size){
        return ticketService.getTicketsByCustomerAndStatus(status, principal, page, size);
    }

    @GetMapping("/stats")
    public List<StatsDto> getTicketStatsByCustomer(Principal principal){
        return ticketService.getTicketStatsByCustomer(principal.getName());
    }

    @GetMapping("/stats/v2")
    public List<StatsDtoV2> getTicketStatsByCustomerV2(Principal principal){
        return ticketService.getTicketStatsByCustomerV2(principal.getName());
    }

    @PutMapping("/update/status/{ticketId}/v1")
    public void updateStatus(@RequestParam TicketStatus ticketStatus,
                             @PathVariable long ticketId,
                             Principal principal){
        ticketService.updateStatus(ticketStatus, ticketId, principal.getName());
    }

    @PutMapping("/update/status/{ticketId}/v2")
    public void updateStatusV2(@RequestParam TicketStatus ticketStatus,
                               @PathVariable long ticketId,
                               Principal principal){

        ticketService.updateStatusWithJpql(ticketStatus, ticketId, principal.getName());
    }

    @GetMapping("/get-by/customer/{id}")
    public List<TicketRespDto> getByCustomerId(@PathVariable long id){
        return ticketService.getByCustomerId(id);
    }
}
