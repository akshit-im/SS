package com.amdocs.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class RecordNotFound extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public RecordNotFound(String record, String type) {
		super(String.format("%s %s Not Found In Database", type, record));
	}
}
