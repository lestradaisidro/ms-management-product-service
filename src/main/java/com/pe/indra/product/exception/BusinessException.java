package com.pe.indra.product.exception;

import org.springframework.http.HttpStatus;

public class BusinessException extends RuntimeException {


    private final HttpStatus status;

    public BusinessException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public BusinessException(String message, Throwable e, HttpStatus status) {
        super(message, e);
        this.status = status;
    }

    public BusinessException(HttpStatus status, String message, HttpStatus status1) {
        super(message);
        this.status = status1;
    }
    public HttpStatus getStatus() {
        return status;
    }
}
