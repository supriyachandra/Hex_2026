package com.hibernate.controller;

import com.hibernate.config.ProjConfig;
import com.hibernate.exception.ResourceNotFoundException;
import com.hibernate.model.Airline;
import com.hibernate.model.Flight;
import com.hibernate.service.FlightService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import java.util.List;
import java.util.Scanner;

public class FlightController {
    public static void main(String[] args) {
        var context = new AnnotationConfigApplicationContext(ProjConfig.class);
        FlightService flightService =
                context.getBean(FlightService.class);

        Scanner sc = new Scanner(System.in);
        while(true){
            System.out.println("1. Insert Airline");
            System.out.println("2. Show all Airlines");
            System.out.println("3. Insert Flight");
            System.out.println("4. Show all Flights");
            System.out.println("5. Update Flight");
            System.out.println("6. Delete Flight by ID");
            System.out.println("0. Exit");
            int input = sc.nextInt();
            if(input == 0)
                break;
            switch(input){
                case 1:
                    Airline airline = new Airline();
                    System.out.println("Enter Name: ");
                    airline.setName(sc.next());
                    System.out.println("Enter Country: ");
                    airline.setCountry(sc.next());
                    flightService.insert(airline);
                    System.out.println("Airline added..");
                    break;

                case 2:
                    List<?> list =   flightService.getAllAirlines();
                    list.forEach(System.out::println);
                    break;
                case 3:
                    System.out.println("Enter the Airline Id this flight belongs to: ");
                    int airlineId = sc.nextInt();
                    try{
                        // Fetch Airline using given ID, else throw exception
                        airline = flightService.getAirlineById(airlineId); //fetching airline
                        // Reading flight info
                        Flight flight = new Flight();
                        System.out.println("Enter flight Number");
                        flight.setFlightNumber(sc.next());
                        System.out.println("Enter flight source");
                        flight.setSource(sc.next());
                        System.out.println("Enter flight destination");
                        flight.setDestination(sc.next());
                        // Read date as String and then parse it to convert to LocalTime in service
                        System.out.println("Enter flight departure time (hh:mm:ss)");
                        String departureTime = sc.next(); // Date read as String
                        // Insert flight in DB
                        flightService.insertFlight(flight,departureTime,airline);
                        System.out.println("Flight added to DB..");
                    }
                    catch (RuntimeException  e){
                        System.out.println(e.getMessage());

                    }
                    break;
                case 4:
                    List<?> listFlight = flightService.fetchAllFlightsWithAirline();
                    listFlight.forEach(System.out::println);
                    break;
                   } //switch ends
        } //while ends
        sc.close();
        context.close();
    } //main ends
}
