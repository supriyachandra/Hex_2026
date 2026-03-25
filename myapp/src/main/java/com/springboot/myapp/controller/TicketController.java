package com.springboot.myapp.controller;

import com.springboot.myapp.dto.FilterReqDto;
import com.springboot.myapp.dto.TicketDto;
import com.springboot.myapp.dto.TicketResPageDto;
import com.springboot.myapp.dto.TicketRespDto;
import com.springboot.myapp.model.Ticket;
import com.springboot.myapp.service.TicketService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/ticket")
public class TicketController {

    private final TicketService ticketService;

    @PostMapping("/add")
    public Ticket insertTicket(@Valid @RequestBody TicketDto ticketReqDto){
        return ticketService.insertTicket(ticketReqDto);
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
}
