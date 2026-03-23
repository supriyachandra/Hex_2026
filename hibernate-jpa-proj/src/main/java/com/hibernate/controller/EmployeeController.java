package com.hibernate.controller;

import com.hibernate.config.ProjConfig;
import com.hibernate.dto.FlightDto;
import com.hibernate.enums.JobTitle;
import com.hibernate.exception.ResourceNotFoundException;
import com.hibernate.model.Airline;
import com.hibernate.model.Employee;
import com.hibernate.service.EmployeeService;
import com.hibernate.service.FlightService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class EmployeeController {
    public static void main(String[] args) {
        var context = new AnnotationConfigApplicationContext(ProjConfig.class);
        EmployeeService employeeService = context.getBean(EmployeeService.class);

        Scanner sc = new Scanner(System.in);
        while(true){
            System.out.println("1. Insert Employee");
            System.out.println("2. Fetch employee and airline details by flight ID");
            System.out.println("3. Fetch employee by JobTitle");
            System.out.println("4. Delete Employee");
            System.out.println("5. Update Employee");
            System.out.println("0. Exit");
            int input = sc.nextInt();
            if(input == 0)
                break;
            switch(input) {
                case 1:
                    Employee employee = new Employee();
                    System.out.println("Enter Employee Name: ");
                    employee.setName(sc.next());
                    System.out.println("Enter Gmail: ");
                    employee.setEmail(sc.next());

                    System.out.println("Enter Job Title: ");
                    String jobTitle= sc.next();

                    System.out.println("Enter Airline ID: ");
                    int airlineId= sc.nextInt();

                    try {
                        employeeService.insert(employee, jobTitle, airlineId);
                        System.out.println("Employee added..");
                    }catch (ResourceNotFoundException | IllegalArgumentException e){
                        System.out.println(e.getMessage());
                    }
                    break;
                case 2:
                    System.out.println("Enter flight ID: ");
                    int flightId= sc.nextInt();

                    FlightDto flightDto= employeeService.fetchEmployeeAndAirlineByFlight(flightId);

                    System.out.println(flightDto.flightNumber() + "\t\t\t " + "Source: "+ flightDto.source());
                    System.out.println(flightDto.airline_name()+ "\t\t\t " + "Destination: "+ flightDto.destination());

                    flightDto.employees().forEach(System.out::println);

                    break;
                case 3:
                    System.out.println("-------Job Titles--------");
                    Arrays.stream(JobTitle.values()).forEach(System.out:: println);
                    System.out.println("Enter Job Title: ");
                    jobTitle = sc.next();
                    try {
                        List<Employee> list = employeeService.getEmployeesByJobTitle(jobTitle);
                        list.forEach(System.out::println);
                    }
                    catch(IllegalArgumentException e){
                        System.out.println("No such Job title exists..");
                    }
                    break;

                case 4:
                    System.out.println("Enter Employee ID: ");
                    int employeeId= sc.nextInt();
                    employeeService.deleteEmployee(employeeId);
                    System.out.println("Employee deleted successfully ");
                    break;

                case 5:
                    int id = 2;
                    Employee updateEmployee1 = new Employee();
                    updateEmployee1.setName("Harry potter");

                    Employee updateEmployee2 = new Employee();
                    updateEmployee2.setName("harry potter");
                    updateEmployee2.setEmail("harry_potter@gmail.com");

                    Employee updateEmployee3 = new Employee();
                    updateEmployee3.setName("ronald weasley");
                    updateEmployee3.setEmail("ron@gmail.com");
                    updateEmployee3.setJobTitle(JobTitle.CREW_MEMBER);
                    employeeService.updateEmployee(updateEmployee3, id);
                    System.out.println("Update done..");
                    break;
                default:
                    System.out.println("Invalid choice ");
            }
        }
    }
}
