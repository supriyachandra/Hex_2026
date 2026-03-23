package com.service;

import com.exception.InvalidPersonException;
import com.model.Person;

import java.util.List;

public class PersonService {

    List<Person> list;

        // Anonymous block, gets called when personService object is created
    {
        list= registerPersons();
    }

    public List<Person> registerPersons() {
        Person p1= new Person(1, "Harry", 21, "London");
        Person p2= new Person(2, "Draco", 6, "Kerch");
        Person p3= new Person(3, "Anne", 20, "Pune");

        return List.of(p1,p2,p3);
    }

    public List<Person> getAdultPersons(){
        return list.stream().
            filter(person -> person.getAge() >= 18)
                .toList();
    }

    public int countNumberOfPerson(List<Person> list){
        if(list == null){
            throw new NullPointerException("List cannot be null");
        }
        return list.size();
    }

    public void validatePerson(Person person){

        // If person is null
        if(person == null){
            throw new NullPointerException("Person cannot be null");
        }

        // If name length is 0 or 1
        if(person.getName().length() <2){
            throw new InvalidPersonException("Person name cannot have less than 1 character");
        }

        // If age is less than 18
        if(person.getAge() <18){
            throw new InvalidPersonException("Person Underage for this operation");
        }
    }
}
