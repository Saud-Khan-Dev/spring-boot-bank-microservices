package com.smways.accounts.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.smways.accounts.dto.ErrorResponseDto;

import jakarta.validation.ConstraintViolationException;

@ControllerAdvice 
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
        MethodArgumentNotValidException ex,
        HttpHeaders headers,
        HttpStatusCode status,
        WebRequest request
    ) {

        Map<String, String> validationErrors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error -> {
            validationErrors.put(error.getField(), error.getDefaultMessage());
        });

        ErrorResponseDto errorResponse = new ErrorResponseDto(
            request.getDescription(false),
            HttpStatus.BAD_REQUEST,
            validationErrors.isEmpty() ? "Validation error" : validationErrors.toString(),
            LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }




    // @Validated on the controller throws this when a @RequestParam fails, e.g. @Pattern on mobileNumber
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponseDto> handleConstraintViolationException(ConstraintViolationException ex, WebRequest webRequest) {
        ErrorResponseDto  errorResponse = new ErrorResponseDto(
            webRequest.getDescription(false),
            HttpStatus.BAD_REQUEST,
            ex.getMessage(),
            LocalDateTime.now()
        );
        return new ResponseEntity<ErrorResponseDto>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CustomerAlreadyExistException.class)
    public ResponseEntity<ErrorResponseDto> handleCustomerAlreadyExistException(CustomerAlreadyExistException ex, WebRequest webRequest) {
       
        ErrorResponseDto  errorResponse = new ErrorResponseDto(
            webRequest.getDescription(false),
            HttpStatus.CONFLICT,
            ex.getMessage(),
            LocalDateTime.now()
        );
        
        return new ResponseEntity<ErrorResponseDto>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest webRequest) {
        ErrorResponseDto  errorResponse = new ErrorResponseDto(
            webRequest.getDescription(false),
            HttpStatus.NOT_FOUND,
            ex.getMessage(),
            LocalDateTime.now()
        );
        return new ResponseEntity<ErrorResponseDto>(errorResponse, HttpStatus.NOT_FOUND);
   }

    // Catch-all for any exception not handled above, so clients always get an ErrorResponseDto
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleGlobalException(Exception ex, WebRequest webRequest) {
        ErrorResponseDto  errorResponse = new ErrorResponseDto(
            webRequest.getDescription(false),
            HttpStatus.INTERNAL_SERVER_ERROR,
            ex.getMessage(),
            LocalDateTime.now()
        );
        return new ResponseEntity<ErrorResponseDto>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}