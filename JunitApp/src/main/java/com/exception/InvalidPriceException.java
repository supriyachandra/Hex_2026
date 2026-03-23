package com.exception;

public class InvalidPriceException extends RuntimeException{

    private String message;

    public InvalidPriceException(String message){
        this.message= message;
    }

    @Override
    public String getMessage(){
        return message;
    }
}
