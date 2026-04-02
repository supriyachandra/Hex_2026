package com.springboot.myapp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "customers")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String city;

    @OneToOne
    private User user;
}
