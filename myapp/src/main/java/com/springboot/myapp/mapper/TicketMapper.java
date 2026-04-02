package com.springboot.myapp.mapper;

import com.springboot.myapp.dto.TicketCustomerDto;
import com.springboot.myapp.dto.TicketDto;
import com.springboot.myapp.dto.TicketRespDto;
import com.springboot.myapp.model.Ticket;

public class TicketMapper {
    public static Ticket mapTo(TicketDto ticketReqDto) {
        Ticket ticket= new Ticket();
        ticket.setSubject(ticketReqDto.subject());
        ticket.setDetails(ticketReqDto.details());
        ticket.setTicketPriority(ticketReqDto.priority());
        return ticket;
    }

    public static TicketRespDto mapToDto(Ticket ticket){
        return new TicketRespDto(
                ticket.getId(),
                ticket.getSubject(),
                ticket.getTicketPriority(),
                ticket.getTicketStatus(),
                ticket.getCreatedAt()
        );
    }

    public static TicketCustomerDto mapToTicketCustomerDto(Ticket ticket){
        return new TicketCustomerDto(
                ticket.getId(),
                ticket.getSubject(),
                ticket.getTicketStatus(),
                ticket.getTicketPriority(),
                ticket.getCreatedAt(),
                ticket.getCustomer().getName(),
                ticket.getExecutive()==null?"YET TO BE ASSIGNED": ticket.getExecutive().getName(),
                ticket.getExecutive()==null? null: ticket.getExecutive().getJobTitle()
        );
    }
}
