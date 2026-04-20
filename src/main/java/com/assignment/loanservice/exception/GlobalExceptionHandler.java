package com.assignment.loanservice.exception;

import com.assignment.loanservice.dto.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

/**
 * Global exception handler for handling application-wide exceptions.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

	/**
	 * Handles LoanDetailNotFoundException.
	 */
	@ExceptionHandler(LoanDetailNotFoundException.class)
	public ResponseEntity<ErrorResponseDTO> handleLoanNotFound(LoanDetailNotFoundException ex) {

		ErrorResponseDTO response = ErrorResponseDTO.builder().status("FAILED").message(ex.getMessage())
				.timestamp(LocalDateTime.now()).build();

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	}

	/**
	 * Handles all generic exceptions.
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponseDTO> handleGenericException(Exception ex) {

		ErrorResponseDTO response = ErrorResponseDTO.builder().status("FAILED")
				.message("Internal Server Error: " + ex.getMessage()).timestamp(LocalDateTime.now()).build();

		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
	}
}