package com.assignment.loanservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO for exposing loan audit data via API.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoanAuditDTO {

	private Long id;
	private String loanAccountNumber;
	private String responseJson;
	private LocalDateTime createdAt;
}