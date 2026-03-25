package com.springboot.myapp.model;

import com.springboot.myapp.enums.TicketPriority;
import com.springboot.myapp.enums.TicketStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Entity
@Table(name= "tickets")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String subject;

    @Column(length = 1000)
    private String details;

    @Enumerated(EnumType.STRING)
    private TicketPriority ticketPriority;

    @Enumerated(EnumType.STRING)
    private TicketStatus ticketStatus;

    @CreationTimestamp
    @Column(updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    private Instant updatedAt;
}
