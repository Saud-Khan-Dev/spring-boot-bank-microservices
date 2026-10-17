package com.smways.loans.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Loan already exists")
public class LoanAlreadyExistsException extends RuntimeException {

    public LoanAlreadyExistsException(String message) {
        super(message);
    }

}
