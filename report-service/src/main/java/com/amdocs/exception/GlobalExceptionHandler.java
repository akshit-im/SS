package com.amdocs.exception;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "GLOBAL_EXCEPTION_HANDLER")
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	public static final String TRACE = "trace";

	@Value("${app.reflectoring.trace}")
	private boolean printStackTrace;

	@ExceptionHandler(LockedException.class)
	@ResponseStatus(HttpStatus.LOCKED)
	public ResponseEntity<Object> handle(LockedException exception) {
		return buildErrorResponse(exception, exception.getMessage(), HttpStatus.LOCKED);
	}

	@ExceptionHandler(DisabledException.class)
	public ResponseEntity<Object> handle(DisabledException exception) {
		return buildErrorResponse(exception, exception.getMessage(), HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(UsernameNotFoundException.class)
	public ResponseEntity<Object> handle(UsernameNotFoundException exception) {
		log.error("UsernameNotFoundException error occurred", exception);
		return buildErrorResponse(exception, exception.getMessage(), HttpStatus.FORBIDDEN);
	}

	@ExceptionHandler(AuthenticationException.class)
	public ResponseEntity<Object> handle(AuthenticationException exception) {
		return buildErrorResponse(exception, exception.getMessage(), HttpStatus.FORBIDDEN);
	}

	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<Object> handle(Exception exception) {
		log.error("Unknown error occurred", exception);
		return buildErrorResponse(exception, exception.getMessage(), HttpStatus.UNAUTHORIZED);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		ErrorResponse errorResponse = new ErrorResponse(HttpStatus.UNPROCESSABLE_ENTITY.value(), "Validation error. Check 'errors' field for details.");
		for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
			errorResponse.addValidationError(fieldError.getField(), fieldError.getDefaultMessage());
		}
		return ResponseEntity.unprocessableEntity().body(errorResponse);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handle(Exception exception, WebRequest request) {
		log.error("Unknown error occurred", exception);
		return buildErrorResponse(exception, "Unknown error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler
	public ResponseEntity<Object> handle(Throwable exception, WebRequest request) {
		ErrorResponse errorResponse = new ErrorResponse(401, exception.getMessage());
		errorResponse.setStackTrace(ExceptionUtils.getStackTrace(exception));
		exception.printStackTrace();
		return ResponseEntity.status(401).body(errorResponse);
	}

	@Override
	public ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		ex.printStackTrace();
		return buildErrorResponse(ex, status);
	}

	private ResponseEntity<Object> buildErrorResponse(Exception exception, HttpStatusCode httpStatus) {
		return buildErrorResponse(exception, exception.getMessage(), httpStatus);
	}

	private ResponseEntity<Object> buildErrorResponse(Exception exception, String message, HttpStatusCode httpStatus) {
		ErrorResponse errorResponse = new ErrorResponse(httpStatus.value(), message);
		if (printStackTrace) {
			errorResponse.setStackTrace(ExceptionUtils.getStackTrace(exception));
		}
		return ResponseEntity.status(httpStatus).body(errorResponse);
	}
}