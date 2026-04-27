package com.springboot.myapp.service;

import com.springboot.myapp.dto.*;
import com.springboot.myapp.enums.Role;
import com.springboot.myapp.enums.TicketStatus;
import com.springboot.myapp.exceptions.AccessNotAllowedException;
import com.springboot.myapp.exceptions.ResourceNotFoundException;
import com.springboot.myapp.mapper.TicketMapper;
import com.springboot.myapp.model.Customer;
import com.springboot.myapp.model.Executive;
import com.springboot.myapp.model.Ticket;
import com.springboot.myapp.model.User;
import com.springboot.myapp.repository.CustomerRepository;
import com.springboot.myapp.repository.ExecutiveRepository;
import com.springboot.myapp.repository.TicketRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
@AllArgsConstructor
public class TicketService {
    private final TicketRepository ticketRepository;
    private final CustomerService customerService;
    private final CustomerRepository customerRepository;
    private final ExecutiveService executiveService;
    private final ExecutiveRepository executiveRepository;
    private final UserService userService;

    public void insertTicket(TicketDto ticketReqDto, String username) {
        // get ticket by username
        Customer customer= customerRepository.getByUsername(username);

       // Convert DTO To Entity using mapper
        Ticket ticket= TicketMapper.mapTo(ticketReqDto);

        // missing fields in entity
        ticket.setTicketStatus(TicketStatus.OPEN);
        // set customer ID
        ticket.setCustomer(customer);

        // save in db using JPARepository commands
        ticketRepository.save(ticket);
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
//        if(filterReqDto.priority()== null && filterReqDto.status()==null){
//            return List.of();
//        }
//
//        TicketPriority priority= (filterReqDto.priority() != null && !filterReqDto.priority().isEmpty())
//                ? TicketPriority.valueOf(filterReqDto.priority())
//                : null;
//
//        TicketStatus status= (filterReqDto.status() != null && !filterReqDto.status().isEmpty())
//                ? TicketStatus.valueOf(filterReqDto.status())
//                : null;

        return ticketRepository.getByPriorityAndStatus(filterReqDto.priority(), filterReqDto.status());
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

    public TicketByCustomerDto getTicketsByCustomerAndStatus(TicketStatus status, Principal principal, int page, int size) {
        // for validation
        User user= (User)userService.loadUserByUsername(principal.getName());
        Customer customer= customerService.getByUsername(principal.getName());

        if(!customer.getUser().getUsername().equals(user.getUsername())){
            throw new AccessNotAllowedException("User cannot access the tickets");
        }

        Pageable pageable= PageRequest.of(page, size);

        Page<Ticket> ticketPage= ticketRepository.getTicketsByCustomerAndStatus(principal.getName(), status, pageable);

        List<TicketCustomerDto> list = ticketPage.stream()
                .map(TicketMapper::mapToTicketCustomerDto)
                .toList();

        long totalRecord= ticketPage.getTotalElements();
        int totalPage= ticketPage.getTotalPages();

        return new TicketByCustomerDto(
                list,
                totalRecord,
                totalPage
        );
    }

    public List<StatsDto> getTicketStatsByCustomer(String username) {
        List<Ticket> ticketList= ticketRepository.getTicketsByUsername(username);

        List<Ticket> openTickets= ticketList.stream()
                .filter(ticket -> ticket.getTicketStatus().equals(TicketStatus.OPEN))
                .toList();

        List<Ticket> closedTickets= ticketList.stream()
                .filter(ticket -> ticket.getTicketStatus().equals(TicketStatus.CLOSED))
                .toList();

        List<Ticket> inProcessTickets= ticketList.stream()
                .filter(ticket -> ticket.getTicketStatus().equals(TicketStatus.IN_PROCESS))
                .toList();

        StatsDto statsDto1= new StatsDto(
                "OPEN TICKETS",
                openTickets.size()
        );
        StatsDto statsDto2= new StatsDto(
                "CLOSED TICKETS",
                closedTickets.size()
        );
        StatsDto statsDto3= new StatsDto(
                "IN_PROCESS TICKETS",
                inProcessTickets.size()
        );

        return List.of(statsDto1, statsDto3, statsDto2);
    }

    public List<StatsDtoV2> getTicketStatsByCustomerV2(String username) {
        return ticketRepository.getTicketStatsByCustomerV2(username);
    }

    public void updateStatus(TicketStatus ticketStatus, long ticketId, String loggedInUsername) {
        Ticket ticket  = ticketRepository.findById(ticketId)
                .orElseThrow(()-> new ResourceNotFoundException("Ticket Id Invalid."));

        // This user is trying to update ticket
        User user = (User) userService.loadUserByUsername(loggedInUsername);

        // Check if the ticket belongs to this user
        //If id of loggedIn user is equal to the id of ticket that needs to be updated. then let it go thru
        //else throw an Exception

        if(user.getRole().equals(Role.CUSTOMER)){
            if( ticket.getCustomer().getUser().getId() != user.getId())
                throw new AccessNotAllowedException("Customer does not own this ticket");

        }
        if(user.getRole().equals(Role.EXECUTIVE)){
            if(ticket.getExecutive() == null)
                throw new AccessNotAllowedException("Executive does not own this ticket");

            if( ticket.getExecutive().getUser().getId() != user.getId())
                throw new AccessNotAllowedException("Executive does not manage this ticket");

        }

        ticket.setTicketStatus(ticketStatus);
        ticketRepository.save(ticket);

    }

    public void updateStatusWithJpql(TicketStatus ticketStatus, long ticketId, String loggedInUsername) {
        Ticket ticket  = ticketRepository.findById(ticketId)
                .orElseThrow(()-> new ResourceNotFoundException("Ticket Id Invalid."));


        // This user is trying to update ticket
        User user = (User) userService.loadUserByUsername(loggedInUsername);

        // Check if the icket belongs to this user
        //If id of loggedIn user is equal to the id of ticket that needs to be updated. then let it go thru
        //else throw an Exception

        if(user.getRole().equals(Role.CUSTOMER)){
            if( ticket.getCustomer().getUser().getId() != user.getId())
                throw new AccessNotAllowedException("Customer does not own this ticket");

        }
        if(user.getRole().equals(Role.EXECUTIVE)){
            if(ticket.getExecutive() == null)
                throw new AccessNotAllowedException("Executive does not own this ticket");

            if( ticket.getExecutive().getUser().getId() != user.getId())
                throw new AccessNotAllowedException("Executive does not manage this ticket");

        }
        ticketRepository.updateStatusWithJpql(ticketStatus,ticketId);
    }

    public List<TicketRespDto> getByCustomerId(long id) {
        Customer customer= customerService.getCustById(id);

        return ticketRepository.getByCustomerId(id).stream().
                map(TicketMapper:: mapToDto)
                .toList();
    }
}
