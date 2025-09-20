package com.lifespring.exception;

public class InvalidUserDetailException extends RuntimeException{

    public InvalidUserDetailException(String message){
        super(message);
    }
}
