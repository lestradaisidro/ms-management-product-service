package com.pe.indra.product.exception;

public class GenericException extends RuntimeException {

	public GenericException(String message) {
		super(message);
	}

	public GenericException(String message, Exception e) {
		super(message, e);
	}
}
