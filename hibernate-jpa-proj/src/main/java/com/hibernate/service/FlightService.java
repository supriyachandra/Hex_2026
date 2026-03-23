package com.hibernate.service;

import com.hibernate.exception.ResourceNotFoundException;
import com.hibernate.model.Airline;
import com.hibernate.model.Flight;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class FlightService {

    @PersistenceContext //This activates EntityManager of Hibernate/JPA
    private EntityManager em;

    @Transactional
    public void insert(Airline airline) {
        em.persist(airline);
    }

    public List<?> getAllAirlines() {
        String jpql="select a from Airline a";
        String hql ="from Airline a";
        Query query =  em.createQuery(hql, Airline.class);
        return query.getResultList();
    }

    public Airline getAirlineById(int airlineId) {
        // Finding Airline by given id
        Airline airline = em.find(Airline.class, airlineId);
        if(airline == null)
            throw new ResourceNotFoundException("Invalid airline id.."); //ID invalid
        return airline;
    }
    @Transactional
    public void insertFlight(Flight flight, String departureTime, Airline airline) {
        // Parse departureTime and attach to flight object
        LocalTime timeOfDeparture = LocalTime.parse(departureTime, DateTimeFormatter.ISO_LOCAL_TIME);
        flight.setDepartureTime(timeOfDeparture);
        // Attach/Set airline object to flight object so that hibernate saves airline_id as Foreign Key in flight
        flight.setAirline(airline);
        // Save the flight object
        em.persist(flight);
    }

    public List<?> fetchAllFlightsWithAirline() {
        String jpql="select f from Flight f";
        String hql ="from Flight f";
        Query query =  em.createQuery(jpql, Flight.class);
        return query.getResultList();
    }

    public Flight getFlightById(int flightId) {
        Flight flight= em.find(Flight.class, flightId);
        if(flight==null){
            throw new ResourceNotFoundException("Invalid Flight ID");
        }
        return flight;
    }
}
/*
* Native SQL: select * from airline --> you query the DB table
* HQL / JPQL :select a from Airline a
* */