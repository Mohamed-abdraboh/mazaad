package com.global.mazaad.security.exception;

public class JwtTokenException extends RuntimeException {

	public JwtTokenException(Throwable cause) {
		super(cause);
	}

	public JwtTokenException(String message) {
		super(message);
	}

	public JwtTokenException(String message, Throwable cause) {
		super(message, cause);
	}
}
