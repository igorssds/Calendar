package com.calendar.fiserv.calendar.controllers.exception;

import javax.persistence.EntityNotFoundException;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<StandardError> validation(MethodArgumentNotValidException e) {
		ValidationError err = new ValidationError(HttpStatus.BAD_REQUEST.value(), "Erro de validação",
				System.currentTimeMillis());

		for (FieldError x : e.getBindingResult().getFieldErrors()) {
			err.addError(x.getField(), x.getDefaultMessage());
		}

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}

	@ExceptionHandler(XlsWithErrorsException.class)
	public ResponseEntity<byte[]> validation(XlsWithErrorsException exception) {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Content-Disposition", "attachment;filename=\"errors.xlsx\"");
		httpHeaders.add("Content-Type", "multipart/form-data");

		return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT).headers(httpHeaders)
				.body(exception.getXlsFile());
	}

	@ExceptionHandler(EmptyResultDataAccessException.class)
	public ResponseEntity<Void> validation(EmptyResultDataAccessException exception) {
		return ResponseEntity.notFound().build();
	}

	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<Void> validation(EntityNotFoundException exception) {
		return ResponseEntity.notFound().build();
	}

	@ExceptionHandler(InvalidHolidayException.class)
	public ResponseEntity<String> validation(InvalidHolidayException exception) {
		return ResponseEntity.badRequest().body(exception.getMessage());
	}
}
