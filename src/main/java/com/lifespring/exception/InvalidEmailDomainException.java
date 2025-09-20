package com.lifespring.exception;

public class InvalidEmailDomainException extends RuntimeException{
    public InvalidEmailDomainException(String message){
        super(message);
    }
}
