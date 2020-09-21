package com.techmahindra.customers.exception;

import org.springframework.http.HttpStatus;

public abstract class ResponseException extends Exception {

	private static final long serialVersionUID = 4435502358629598161L;

	public ResponseException() {
		super();
	}

	public ResponseException(String message, Throwable cause) {
		super(message, cause);
	}

	public ResponseException(String message) {
		super(message);
	}

	public ResponseException(Throwable cause) {
		super(cause);
	}

	public abstract HttpStatus getHttpStatus();
}
