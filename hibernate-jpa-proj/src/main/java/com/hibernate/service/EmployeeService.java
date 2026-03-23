package com.hibernate.service;

import com.hibernate.dto.FlightDto;
import com.hibernate.enums.JobTitle;
import com.hibernate.model.Airline;
import com.hibernate.model.Employee;
import com.hibernate.model.Flight;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EmployeeService {

    private final FlightService flightService;

    @PersistenceContext
    private EntityManager em;

    public EmployeeService(FlightService flightService) {
        this.flightService = flightService;
    }

    @Transactional
    public void insert(Employee employee, String jobTitle, int airlineId) {

        // convert job title string to enum
        JobTitle title= JobTitle.valueOf(jobTitle);
        employee.setJobTitle(title);

        // check if airlineId exists
        Airline airline= flightService.getAirlineById(airlineId);

        employee.setArline(airline);
        //insert airline and employee persist
        em.persist(employee);
    }

    public FlightDto fetchEmployeeAndAirlineByFlight(int flightId) {

        // get flight by ID
        Flight flight= flightService.getFlightById(flightId);

//        String jpql="select e from Employee e where e.airline.id=:airlineId";
//        Query query=em.createQuery(jpql,Employee.class);
//        query.setParameter("airlineId",flight.getAirline().getId());
//        List<Employee>list=query.getResultList();

        // select employee details by flight
        String jpql= "select e from Employee e where e.airline.id =: flightId";
        Query query= em.createQuery(jpql, Employee.class);
        query.setParameter("flightId", flightId);

        List<Employee> e= query.getResultList();

        return new FlightDto(
                flight.getFlightNumber(),
                flight.getSource(),
                flight.getDestination(),
                flight.getAirline().getName(),
                e
        );
    }

    public List<Employee> getEmployeesByJobTitle(String jobTitle) { //using criteria query
        // Step 0. Validate enum value
        JobTitle.valueOf(jobTitle);
        // 1. Build the CriteriaQuery using the Builder : get Builder
        CriteriaBuilder cb= em.getCriteriaBuilder();

        // 2. Define the Result Type by creating object : select
        CriteriaQuery<Employee> ctq= cb.createQuery(Employee.class);

        // 3. Define the from clause : from
        Root<Employee> employeeRoot= ctq.from(Employee.class);

        // 4. Define the where clause : where (predicate of CQ) jobTitle = ?
        Predicate predicate= cb.equal(employeeRoot.get("jobTitle"), jobTitle);
        ctq.where(predicate);

        // Execute the criteria query : execution
        return em.createQuery(ctq).getResultList();

    }

    @Transactional
    public void deleteEmployee(int employeeId) {
        String jpqa= "delete from Employee e where e.id=:employeeId";
        em.createQuery(jpqa).setParameter("employeeId", employeeId).executeUpdate();
    }

    @Transactional
    public void updateEmployee(Employee updateEmployee, int id) {
        // create cb
        CriteriaBuilder cb= em.getCriteriaBuilder();

        // No need to set return type because update is returning void
        // Update Criteria updateQuery
        CriteriaUpdate<Employee> updateCriteria= cb.createCriteriaUpdate(Employee.class);

        // root, from
        Root<Employee> employee= updateCriteria.from(Employee.class);

        // Set
        if(updateEmployee.getName() != null && !updateEmployee.getName().isEmpty()){
            updateCriteria.set(employee.get("name"), updateEmployee.getName());
        }

        if(updateEmployee.getEmail() != null && !updateEmployee.getEmail().isEmpty()){
            updateCriteria.set(employee.get("email"), updateEmployee.getEmail());
        }

        if(updateEmployee.getJobTitle() != null ){
            updateCriteria.set(employee.get("jobTitle"), updateEmployee.getJobTitle());
        }

        // 5. Define the where clause : where (predicate of CQ) id = ?
        Predicate predicate= cb.equal(employee.get("id"), id);
        updateCriteria.where(predicate);

        // 6. Execute the criteria query : execution
        em.createQuery(updateCriteria).executeUpdate();

    }
}
