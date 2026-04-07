package com.project.amazecare.exception;

public class PatientAlreadyExistsException extends RuntimeException{
    public PatientAlreadyExistsException(String message){
        super(message);
    }
}
