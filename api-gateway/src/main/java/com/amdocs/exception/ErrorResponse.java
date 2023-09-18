package com.amdocs.exception;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import com.amdocs.general.AppConstant;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse implements Serializable {

	private static final long serialVersionUID = 5926468583005150707L;

	public ErrorResponse(Integer status, String message) {
		super();
		this.status = status;
		this.message = message;
	}

	public ErrorResponse(Integer status, String message, String stackTrace) {
		super();
		this.status = status;
		this.message = message;
		this.stackTrace = stackTrace;
	}

	public ErrorResponse(Integer status, String message, String stackTrace, String remedy) {
		super();
		this.status = status;
		this.message = message;
		this.remedy = remedy;
		this.stackTrace = stackTrace;
	}

	private Integer status;

	private String message;

	private String path;

	private String remedy;

	@JsonFormat(pattern = AppConstant.DATE_PATTERN)
	private Date timestamp = new Date();

	private String stackTrace;

	private List<ValidationError> errors;

	@Data
	@RequiredArgsConstructor
	private static class ValidationError {
		private final String field;
		private final String message;
	}

	public void addValidationError(String field, String message) {
		if (Objects.isNull(errors)) {
			errors = new ArrayList<>();
		}
		errors.add(new ValidationError(field, message));
	}

}
