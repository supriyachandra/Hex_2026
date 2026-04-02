package com.springboot.myapp.service;

import com.springboot.myapp.dto.TicketRespDto;
import com.springboot.myapp.enums.TicketPriority;
import com.springboot.myapp.enums.TicketStatus;
import com.springboot.myapp.exceptions.ResourceNotFoundException;
import com.springboot.myapp.model.Ticket;
import com.springboot.myapp.repository.TicketRepository;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test; //Junit 5
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TicketServiceTest {

    @InjectMocks
    private TicketService ticketService;

    @Mock
    private TicketRepository ticketRepository;

    @Test
    public void getByIdIfExistsTest(){
        // check if ticketService is not null
        Assertions.assertNotNull(ticketService);

        // preparing the data
        Ticket ticket= new Ticket();
        ticket.setId(22L);
        ticket.setSubject("subject test");
        ticket.setTicketPriority(TicketPriority.HIGH);
        ticket.setTicketStatus(TicketStatus.IN_PROCESS);
        ticket.setCreatedAt(Instant.now());

        TicketRespDto dto= new TicketRespDto(
                ticket.getId(),
                ticket.getSubject(),
                ticket.getTicketPriority(),
                ticket.getTicketStatus(),
                ticket.getCreatedAt()
        );

        TicketRespDto dto2= new TicketRespDto(
                ticket.getId(),
                ticket.getSubject(),
                TicketPriority.LOW,
                ticket.getTicketStatus(),
                ticket.getCreatedAt()
        );
        // use this ticket for mocking using when and then if findById(22L) is called
        when(ticketRepository.findById(22L)).thenReturn(Optional.of(ticket));

        Assertions.assertEquals(dto, ticketService.getById(22L));

        Assertions.assertNotEquals(dto2, ticketService.getById(22L));

        // number of times repo should be called
        Mockito.verify(ticketRepository, Mockito.times(2)).findById(22L);
    }

    @Test
    public void getByIdIfNotExistsTest(){
        // if 10 is passed, return empty object (id not found)
        when(ticketRepository.findById(10L)).thenReturn(Optional.empty());

        // Check if exception is thrown (store in e so we can check message too)
        Exception e= Assertions.assertThrows(ResourceNotFoundException.class,
                ()-> ticketService.getById(10L)
        );

        // check if exception message is same
        Assertions.assertEquals("Invalid ID Given", e.getMessage());

        // check if repo is only called once
        Mockito.verify(ticketRepository, Mockito.times(1)).findById(10L);
    }

    @Test
    public void getAllTicketsTest(){
        /* Prepare the List. */
        Ticket ticket1 = new Ticket();
        ticket1.setId(12L);
        ticket1.setSubject("test subject");
        ticket1.setTicketPriority(TicketPriority.LOW);
        ticket1.setTicketStatus(TicketStatus.OPEN);
        ticket1.setCreatedAt(Instant.now());
        Ticket ticket2 = new Ticket();
        ticket2.setId(14L);
        ticket2.setSubject("test subject");
        ticket2.setTicketPriority(TicketPriority.HIGH);
        ticket2.setTicketStatus(TicketStatus.CLOSED);
        ticket2.setCreatedAt(Instant.now());
        List<Ticket> list = List.of(ticket1,ticket2);

        // Create a pageObj: pageTicket for page is 0 and size as 2
        Page<Ticket> pageTicket = new PageImpl<>(list);
        int page = 0;
        int size = 2;

        Pageable pageable = PageRequest.of(page,size);
        // Mock the repository call for findALL()
        // when(ticketRepository.findAll(pageable)).thenReturn(pageTicket);

        // Create a pageObj: pageTicket1 for page is 0 and size as 1
        Page<Ticket> pageTicket1 = new PageImpl<>(list.subList(0,1));
        page = 0;
        size = 1;

        Pageable pageable1 = PageRequest.of(page,size);
        // Mock the repository call for findALL()
        when(ticketRepository.findAll(pageable1)).thenReturn(pageTicket1);
        // Actual Call
        //Assertions.assertEquals(2 , ticketService.getAllTickets(0,2).data().size());
        Assertions.assertEquals(1 , ticketService.getAllTickets(0,1).data().size());
        //Assertions.assertNotEquals(3 , ticketService.getAllTickets(0,1).data().size());
    }

}
