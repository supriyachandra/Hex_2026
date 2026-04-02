package com.springboot.myapp.service;

import com.springboot.myapp.dto.*;
import com.springboot.myapp.enums.TicketPriority;
import com.springboot.myapp.enums.TicketStatus;
import com.springboot.myapp.exceptions.ResourceNotFoundException;
import com.springboot.myapp.mapper.TicketMapper;
import com.springboot.myapp.model.Customer;
import com.springboot.myapp.model.Executive;
import com.springboot.myapp.model.Ticket;
import com.springboot.myapp.repository.CustomerRepository;
import com.springboot.myapp.repository.ExecutiveRepository;
import com.springboot.myapp.repository.TicketRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TicketService {
    private final TicketRepository ticketRepository;
    private final CustomerService customerService;
    private final CustomerRepository customerRepository;
    private final ExecutiveService executiveService;
    private final ExecutiveRepository executiveRepository;

    public Ticket insertTicket(TicketDto ticketReqDto, String username) {
        // get ticket by username
        Customer customer= customerRepository.getByUsername(username);

       // Convert DTO To Entity using mapper
        Ticket ticket= TicketMapper.mapTo(ticketReqDto);

        // missing fields in entity
        ticket.setTicketStatus(TicketStatus.OPEN);
        // set customer ID
        ticket.setCustomer(customer);

        // save in db using JPARepository commands
        return ticketRepository.save(ticket);
    }

    public TicketResPageDto getAllTickets(int page, int size) {
        // return list of tickets with pagination
        // use pageable interface for pagination
        Pageable pageable= PageRequest.of(page, size);
        Page<Ticket> pageTicket= ticketRepository.findAll(pageable);

        List<TicketRespDto> ticketRespDtoList= pageTicket.toList()
                .stream()
                .map(TicketMapper :: mapToDto)
                .toList();

        Long totalRecord= pageTicket.getTotalElements();
        int totalPage= pageTicket.getTotalPages();

        return new TicketResPageDto(
                ticketRespDtoList,
                totalRecord,
                totalPage
        );
    }

    public TicketRespDto getById(long id) {
        Ticket ticket= ticketRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Invalid ID Given"));
        return new TicketRespDto(
                ticket.getId(),
                ticket.getSubject(),
                ticket.getTicketPriority(),
                ticket.getTicketStatus(),
                ticket.getCreatedAt()
        );
    }


    public List<TicketRespDto> getByFilter(FilterReqDto filterReqDto) {
        if(filterReqDto.priority()== null && filterReqDto.status()==null){
            return List.of();
        }

        TicketPriority priority= (filterReqDto.priority() != null && !filterReqDto.priority().isEmpty())
                ? TicketPriority.valueOf(filterReqDto.priority())
                : null;

        TicketStatus status= (filterReqDto.status() != null && !filterReqDto.status().isEmpty())
                ? TicketStatus.valueOf(filterReqDto.status())
                : null;

        return ticketRepository.getByPriorityAndStatus(priority, status);
    }

    public void addExecutiveToTicket(long ticketId, long executiveId) {
        // get ticket
        Ticket ticket= ticketRepository.findById(ticketId).orElseThrow(
                ()-> new ResourceNotFoundException("Ticket ID Invalid")
        );
        // executive by ID
        Executive executive= executiveRepository.findById(executiveId).
                orElseThrow(()-> new ResourceNotFoundException("Executive ID Invalid!"));
        //inject executive into ticket
        ticket.setExecutive(executive);
        // save ticket again
        ticketRepository.save(ticket);
    }

    public List<TicketCustomerDto> getTicketByCustomerId(long customerId) {
        // validate customer id
        customerService.getById(customerId);

        return ticketRepository.getTicketByCustomerId(customerId);
        // get list of tickets
    }

    public List<TicketCustomerDto> getTicketsByUsername(String username) {
        List<Ticket> ticketList= ticketRepository.getTicketsByUsername(username);

        return ticketList.stream()
                .map(TicketMapper:: mapToTicketCustomerDto)
                .toList();
    }
}
