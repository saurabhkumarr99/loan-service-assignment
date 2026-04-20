package com.assignment.loanservice.controller;

import com.assignment.loanservice.dto.ApiResponseDTO;
import com.assignment.loanservice.dto.LoanDTO;
import com.assignment.loanservice.service.LoanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * REST controller for loan-related APIs.
 */
@RestController
@RequestMapping("/loan")
@RequiredArgsConstructor
public class LoanController {

	private final LoanService loanService;

	/**
	 * Fetch loan details by loan account number.
	 */
	@GetMapping("/{loanAccountNumber}")
	public ResponseEntity<ApiResponseDTO<LoanDTO>> getLoanDetails(@PathVariable String loanAccountNumber) {

		LoanDTO loan = loanService.getLoanDetails(loanAccountNumber);

		ApiResponseDTO<LoanDTO> response = ApiResponseDTO.<LoanDTO>builder().status("SUCCESS")
				.message("Loan details fetched successfully").timestamp(LocalDateTime.now()).data(loan).build();

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
}