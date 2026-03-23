package com.hibernate.model;

import com.hibernate.enums.JobTitle;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Entity
public class Employee {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name= "job_title")
    private JobTitle jobTitle;

    @CreationTimestamp
    @Column(name="created_at")
    private Instant createdAt;

    @ManyToOne
    private Airline airline;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public JobTitle getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(JobTitle jobTitle) {
        this.jobTitle = jobTitle;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Airline getArline() {
        return airline;
    }

    public void setArline(Airline airline) {
        this.airline = airline;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", jobTitle=" + jobTitle +
                ", createdAt=" + createdAt +
                ", airline=" + airline +
                '}';
    }
}
