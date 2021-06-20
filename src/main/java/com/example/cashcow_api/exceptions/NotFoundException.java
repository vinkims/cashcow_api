package com.example.cashcow_api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {
    
    private static final long serialVersionUID = 1L;

    private String exceptionMsg;

    private String invalidField;

    public NotFoundException(String exception){
        super(exception);
        this.exceptionMsg = exception;
    }

    public NotFoundException(String exception, String field){
        super(exception);
        this.exceptionMsg = exception;
        this.invalidField = field;
    }

    /**
     * return the exceptionMsg
     */
    public String getExceptionMsg(){
        return exceptionMsg;
    }

    /**
     * return the field
     */
    public String getInvalidField(){
        return invalidField;
    }
}
