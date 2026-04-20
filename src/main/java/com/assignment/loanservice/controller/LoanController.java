package com.assignment.loanservice.controller;

import com.assignment.loanservice.dto.ApiResponseDTO;
import com.assignment.loanservice.dto.LoanDTO;
import com.assignment.loanservice.service.LoanService;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * REST controller for loan-related APIs.
 */
@RestController
@RequestMapping("/loan")
@RequiredArgsConstructor
@Validated
public class LoanController {

	private final LoanService loanService;

	/**
	 * Fetch loan details by loan account number.
	 */
	@GetMapping("/{loanAccountNumber}")
	public ResponseEntity<ApiResponseDTO<LoanDTO>> getLoanDetails(@PathVariable @NotBlank(message = "Loan AccountNumber is required") @Pattern(regexp = "\\d+", message = "Loan Account Number must be numeric") String loanAccountNumber) {

		LoanDTO loan = loanService.getLoanDetails(loanAccountNumber);

		ApiResponseDTO<LoanDTO> response = ApiResponseDTO.<LoanDTO>builder().status("SUCCESS")
				.message("Loan details fetched successfully").timestamp(LocalDateTime.now()).data(loan).build();

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
}