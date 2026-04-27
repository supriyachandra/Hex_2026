package com.project.bookmanagement.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

@Entity
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Size(min = 3)
    private String name;

    private String address;

    @OneToOne
    private User user;
}
