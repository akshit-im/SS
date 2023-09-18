package com.amdocs.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UnAuthorizedException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private String message;

	public UnAuthorizedException(String message) {
		super(message);
		this.message = message;
	}

}