package com.pe.indra.product.exception;

import org.springframework.http.HttpStatus;

public class RepositoryException extends RuntimeException {


    private final HttpStatus status;

    public RepositoryException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public RepositoryException(String message, Throwable e, HttpStatus status) {
        super(message, e);
        this.status = status;
    }

    public RepositoryException(HttpStatus status, String message, HttpStatus status1) {
        super(message);
        this.status = status1;
    }
    public HttpStatus getStatus() {
        return status;
    }

}
