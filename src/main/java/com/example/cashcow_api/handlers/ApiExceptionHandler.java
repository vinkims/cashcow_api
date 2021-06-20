package com.example.cashcow_api.handlers;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.example.cashcow_api.exceptions.InvalidInputException;
import com.example.cashcow_api.exceptions.NotFoundException;
import com.example.cashcow_api.responses.InvalidArgumentResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {
    
    Logger logger = LoggerFactory.getLogger(ApiExceptionHandler.class);

    /**
     * Handles exceptions due to invalid input
     */
    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, 
            HttpHeaders headers, HttpStatus status, WebRequest request){

        String message = "Invalid input field in request body";

        BindingResult bindingResult = ex.getBindingResult();
        Map<String, Object> errors = new HashMap<String, Object>();
        if (bindingResult.hasFieldErrors()){
            for (FieldError err : ex.getBindingResult().getFieldErrors()){
                String key = err.getField();
                String msg = err.getDefaultMessage();
                errors.put(key, msg);
            }
        }

        if (bindingResult.hasGlobalErrors()){
            errors.put("generalError", bindingResult.getGlobalError().getDefaultMessage());
        }

        InvalidArgumentResponse exceptionResponseBody = new InvalidArgumentResponse(
            new Date().toString(), message, errors, status.value()
        );

        return new ResponseEntity<Object>(exceptionResponseBody, headers, status);
    }

    /**
     * Handles invalid input exceptions
     */
    @ExceptionHandler({ InvalidInputException.class})
    public ResponseEntity<Object> handleInvalidInputException(InvalidInputException ex, WebRequest request){

        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = "Invalid value provided";
        Map<String, Object> errors = new HashMap<String, Object>();
        errors.put(ex.getInvalidField(), ex.getExceptionMsg());

        InvalidArgumentResponse invalidArgumentResp = new InvalidArgumentResponse(
            new Date().toString(), message, errors, status.value()
        );

        logger.error("{}: [MSG] {} -> {}", status.value(), message, errors);

        return new ResponseEntity<>(invalidArgumentResp, new HttpHeaders(), status);
    }

    /**
     * Handles not found exceptions
     */
    @ExceptionHandler({ NotFoundException.class})
    public ResponseEntity<Object> handleNotFoundException(NotFoundException ex, WebRequest request){

        HttpStatus status = HttpStatus.NOT_FOUND;
        String message = "Invalid value provided";
        Map<String, Object> errors = new HashMap<String, Object>();
        errors.put(ex.getInvalidField(), ex.getExceptionMsg());

        InvalidArgumentResponse invalidArgumentResp = new InvalidArgumentResponse(
            new Date().toString(), message, errors, status.value()
        );

        logger.error("{}: [MSG] {} -> {}", status.value(), message, errors);

        return new ResponseEntity<>(invalidArgumentResp, new HttpHeaders(), status);
    }
}
