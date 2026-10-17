package com.smways.accounts.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.CONFLICT, reason="Customer already exists") 
public class CustomerAlreadyExistException extends RuntimeException {
    
    public CustomerAlreadyExistException(String message) {
        super(message);
    }
    
}
