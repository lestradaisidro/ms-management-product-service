package com.pe.indra.product.infrastructure.rest;

import com.pe.indra.product.exception.BusinessException;
import com.pe.indra.product.exception.GenericException;
import com.pe.indra.product.exception.RepositoryException;
import com.pe.indra.product.infrastructure.rest.model.ErrorResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;

@RestControllerAdvice
@RequiredArgsConstructor
public class ErrorHandler {

    @ExceptionHandler(GenericException.class)
    public Mono<ResponseEntity<ErrorResponse>> handleGenericException(GenericException ex) {
        return Mono.just(ResponseEntity.status(500).body(ErrorResponse.builder().code(500).description(ex.getMessage()).build()));
    }

    @ExceptionHandler(RepositoryException.class)
    public Mono<ResponseEntity<ErrorResponse>> handleRepositoryException(RepositoryException ex) {
        return Mono.just(ResponseEntity.status(ex.getStatus()).body(ErrorResponse.builder().code(ex.getStatus().value()).description(ex.getMessage()).build()));
    }
    @ExceptionHandler(BusinessException.class)
    public Mono<ResponseEntity<ErrorResponse>> handleBusinessException(BusinessException ex) {
        return Mono.just(ResponseEntity.status(ex.getStatus()).body(ErrorResponse.builder().code(ex.getStatus().value()).description(ex.getMessage()).build()));
    }



}
