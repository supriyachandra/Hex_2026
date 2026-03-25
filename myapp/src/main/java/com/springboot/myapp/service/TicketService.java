package com.springboot.myapp.service;

import com.springboot.myapp.dto.FilterReqDto;
import com.springboot.myapp.dto.TicketDto;
import com.springboot.myapp.dto.TicketResPageDto;
import com.springboot.myapp.dto.TicketRespDto;
import com.springboot.myapp.enums.TicketPriority;
import com.springboot.myapp.enums.TicketStatus;
import com.springboot.myapp.exceptions.ResourceNotFoundException;
import com.springboot.myapp.mapper.TicketMapper;
import com.springboot.myapp.model.Ticket;
import com.springboot.myapp.repository.TicketRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Service
@AllArgsConstructor
public class TicketService {
    private final TicketRepository ticketRepository;

    public Ticket insertTicket(@Valid TicketDto ticketReqDto) {
       // Convert DTO To Entity using mapper
        Ticket ticket= TicketMapper.mapTo(ticketReqDto);

        // missing fields in entity
        ticket.setTicketStatus(TicketStatus.OPEN);

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
}
