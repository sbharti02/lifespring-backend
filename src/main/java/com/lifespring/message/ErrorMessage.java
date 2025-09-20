package com.lifespring.message;

import lombok.Builder;
import org.springframework.stereotype.Component;

@Builder
public class ErrorMessage {

    private String message;
    private int statusCode;
    private String details;
    private String path;
    public ErrorMessage(String message, int statusCode, String details,String path) {
        this.message = message;
        this.statusCode = statusCode;
        this.details = details;
        this.path = path;
    }

    public ErrorMessage() {
    }
//This is the get method

    public String getMessage(){
        return this.message;
    }

    public int getStatusCode(){
        return this.statusCode;
    }
    public String getDetails(){
        return this.details;
    }

    public String getPath(){
        return this.path;
    }

    //This is the setter methods

    public void setStatusCode(int statusCode){
        this.statusCode = statusCode;
    }

    public void setPath(String path ){
        this.path = path;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public void setMessage(String message){
        this.message = message;
    }
}
