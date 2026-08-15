package com.mit.scratchspringboot.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.mit.scratchspringboot.dto.ErrorResponse;

import jakarta.servlet.http.HttpServletRequest;
@RestControllerAdvice
class GlobalExceptionHandler {

	@ExceptionHandler(RecordNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleEmployeeNotFound(RecordNotFoundException ex,
			HttpServletRequest request) {

		ErrorResponse response = new ErrorResponse(LocalDateTime.now().toString(), HttpStatus.NOT_FOUND.value(),
				"Record Not Found", ex.getMessage(), request.getRequestURI());

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);//404
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleGenericException(Exception ex, HttpServletRequest request) {

		ErrorResponse response = new ErrorResponse(LocalDateTime.now().toString(),
				HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal Server Error", "Something went wrong",
				request.getRequestURI());

		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);//500
	}
	
	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<ErrorResponse> handleGenericException(RuntimeException ex, HttpServletRequest request) {

		ErrorResponse response = new ErrorResponse(LocalDateTime.now().toString(),
				HttpStatus.BAD_REQUEST.value(), "Bed request ", " bed request",
				request.getRequestURI());

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);//400
	}

}
