package com.adex.codingchallenge.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus
public class InvalidRequestException extends RuntimeException {
    
    public InvalidRequestException (String message){
        super(message);
    }

    public InvalidRequestException (String message, Throwable throwable){
        super(message, throwable);
    }
}
