package com.example.exxtest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.ACCEPTED)
public class BetaServiceException extends RuntimeException {
    private String errorCode;

    public BetaServiceException(String errorMessage){
        super(errorMessage);
    }

    public BetaServiceException(String errorCode,String errorMessage){
        super(errorMessage);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
}
