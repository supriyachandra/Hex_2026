package com.springboot.myapp.repository;

import com.springboot.myapp.dto.FilterReqDto;
import com.springboot.myapp.dto.TicketRespDto;
import com.springboot.myapp.enums.TicketPriority;
import com.springboot.myapp.enums.TicketStatus;
import com.springboot.myapp.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface TicketRepository extends JpaRepository<Ticket, Long> {

    @Query("""
            select t.id, t.subject, t.ticketPriority, t.ticketStatus, t.createdAt
            from Ticket t
            where (?1 is Null or t.ticketPriority= ?1)
            and (?2 is NULL or t.ticketStatus= ?2)
        """)
    List<TicketRespDto> getByPriorityAndStatus(TicketPriority priority, TicketStatus status);
}
