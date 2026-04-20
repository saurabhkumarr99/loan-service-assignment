package com.assignment.loanservice.controller;

import com.assignment.loanservice.dto.ApiResponseDTO;
import com.assignment.loanservice.dto.LoanAuditDTO;
import com.assignment.loanservice.service.LoanAuditService;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * REST controller for Loan Audit operations. Exposes endpoints to fetch audit
 * history.
 */
@RestController
@RequestMapping("/loan/audit")
@RequiredArgsConstructor
@Validated
public class LoanAuditController {

	private final LoanAuditService loanAuditService;

	/**
	 * Get all audit logs for loan API calls.
	 *
	 * @return standardized API response with audit data
	 */
	@GetMapping
	public ResponseEntity<ApiResponseDTO<List<LoanAuditDTO>>> getAllAudits() {

		List<LoanAuditDTO> audits = loanAuditService.getAllAuditLogs();

		ApiResponseDTO<List<LoanAuditDTO>> response = ApiResponseDTO.<List<LoanAuditDTO>>builder().status("SUCCESS")
				.message("Audit logs fetched successfully").timestamp(LocalDateTime.now()).data(audits).build();

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	/**
	 * Get audit logs by loan account number.
	 *
	 * @param loanAccountNumber loan account number
	 * @return filtered audit logs
	 */
	@GetMapping("/{loanAccountNumber}")
	public ResponseEntity<ApiResponseDTO<List<LoanAuditDTO>>> getAuditByLoanAccount(
			@PathVariable @NotBlank(message = "Loan AccountNumber is required") @Pattern(regexp = "\\d+", message = "Loan Account Number must be numeric") String loanAccountNumber) {

		List<LoanAuditDTO> audits = loanAuditService.getAuditByLoanAccountNumber(loanAccountNumber);

		return ResponseEntity.ok(ApiResponseDTO.<List<LoanAuditDTO>>builder().status("SUCCESS")
				.message("Audit logs fetched for account: " + loanAccountNumber).timestamp(LocalDateTime.now())
				.data(audits).build());
	}
}