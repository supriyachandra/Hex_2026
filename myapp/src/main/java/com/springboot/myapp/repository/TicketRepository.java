package com.springboot.myapp.repository;

import com.springboot.myapp.dto.*;
import com.springboot.myapp.enums.TicketPriority;
import com.springboot.myapp.enums.TicketStatus;
import com.springboot.myapp.model.Ticket;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.security.Principal;
import java.util.List;


public interface TicketRepository extends JpaRepository<Ticket, Long> {

    @Query("""
            select t.id, t.subject, t.ticketPriority, t.ticketStatus, t.createdAt
            from Ticket t
            where (?1 is Null or t.ticketPriority= ?1)
            and (?2 is NULL or t.ticketStatus= ?2)
        """)
    List<TicketRespDto> getByPriorityAndStatus(TicketPriority priority, TicketStatus status);

    @Query("""
                select t.id, t.subject, t.ticketStatus, t.ticketPriority,
                t.createdAt, t.customer.name, t.executive.name, t.executive.jobTitle
                from Ticket t where t.customer.id= ?1
                """)
    List<TicketCustomerDto> getTicketByCustomerId(long customerId);

    @Query("select t from Ticket t where t.customer.user.username=?1")
    List<Ticket> getTicketsByUsername(String username);

    @Query("select t from Ticket t where t.customer.user.username=?1 and t.ticketStatus=?2")
    Page<Ticket> getTicketsByCustomerAndStatus(String username, TicketStatus status, Pageable pageable);

    @Query("""
          select new com.springboot.myapp.dto.StatsDtoV2(t.ticketStatus, count(t.id))
          from Ticket t where
          t.customer.user.username= ?1
          group by t.ticketStatus
          """)
    List<StatsDtoV2> getTicketStatsByCustomerV2(String username);

    @Modifying
    @Transactional
    @Query("""
            update Ticket t
            SET t.ticketStatus = ?1
            where t.id = ?2
            """)
    void updateStatusWithJpql(TicketStatus ticketStatus, long ticketId);

    @Query("select t from Ticket t where t.customer.id= ?1")
    List<Ticket> getByCustomerId(long id);
}
/*
id
subject
status
priority
createdAt
customerName
executiveName
executiveJobTitle
 */