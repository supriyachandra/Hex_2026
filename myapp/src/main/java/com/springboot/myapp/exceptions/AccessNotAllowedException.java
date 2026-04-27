package com.springboot.myapp.exceptions;

public class AccessNotAllowedException extends RuntimeException{
    public AccessNotAllowedException(String message){
        super(message);
    }
}
