package com.service;

import com.exception.InvalidPersonException;
import com.model.Person;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class PersonServiceTest {

    private PersonService personService;

    @BeforeEach
    public void init(){
        personService= new PersonService();
    }

    @AfterEach
    public void finish(){
        personService= null;
    }



    @Test
    public void countNumberOfPersonTest(){
        List<Person> list= new ArrayList<>();
        Person p1= new Person(1, "Harry Potter", 19, "London");
        Person p2= new Person(2, "Kaz Brekker", 23, "Kerch");
        Person p3= new Person(3, "Anne Shirley Cuthbert", 12, "Green Gables");

        //list has 3 elements
        list.add(p1);
        list.add(p2);
        list.add(p3);

        //list1 has 2 elements
        List<Person> list1= new ArrayList<>();
        list1.add(p1);
        list1.add(p2);

        //list2 is null
        List<Person> list2= null;

        //list3 size is 0
        List<Person> list3= new ArrayList<>();
        list3.add(p3);

        Assertions.assertEquals(3, personService.countNumberOfPerson(list));
        Assertions.assertEquals(2, personService.countNumberOfPerson(list1));
        Assertions.assertEquals(1, personService.countNumberOfPerson(list3));

        Assertions.assertThrows(NullPointerException.class,
                ()-> personService.countNumberOfPerson((list2)));
    }


    @Test
    public void validatePersonTest(){
        // Check for null Person
        NullPointerException e=
                Assertions.assertThrows(NullPointerException.class,
                        () -> personService.validatePerson(null));

        Assertions.assertEquals("Person cannot be null", e.getMessage());


        // Preparing for check
        Person p1= new Person(1, "H", 19, "Kerch");

        // Check for person name length
        InvalidPersonException e1=
                Assertions.assertThrows(InvalidPersonException.class,
                        () -> personService.validatePerson(p1));
        Assertions.assertEquals("Person name cannot have less than 1 character", e1.getMessage());


        //Preparing for next check
        Person p2= new Person(1, "Harry", 9, "Kerch");

        // Check for person age
        InvalidPersonException e2=
                Assertions.assertThrows(InvalidPersonException.class,
                        ()-> personService.validatePerson(p2));
        Assertions.assertEquals("Person Underage for this operation", e2.getMessage());

        // not underage, exception should not be thrown.
        Person p3= new Person(1, "Harry", 29, "Kerch");
        Assertions.assertDoesNotThrow(()->personService.validatePerson(p3));

    }


    @Test
    public void getAdultPersonsTest(){

        //check if there are 2 adults
        Assertions.assertEquals(2, personService.getAdultPersons().size(), "There are not 2 Adults in the list");

        // check if there is any minor
        Assertions.assertTrue(personService.registerPersons()
                .stream()
                .anyMatch(person -> person.getAge() <18), "Oops! There is no underage person");

        // check for false ie underage person not available
        Assertions.assertTrue(personService.registerPersons()
                .stream()
                .anyMatch(person -> person.getAge() <18), "Oops! There is no underage person");


    }
}
