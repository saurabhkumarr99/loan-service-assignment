package com.assignment.loanservice.dto;

import lombok.Data;

import java.util.List;

/**
 * Processed loan response.
 */
@Data
public class LoanDTO {

	private String loanAccountNumber;
	private List<LoanDetail> loanDetails;

	@Data
	public static class LoanDetail {
		private String dueDate;
		private Double emiAmount;
	}
}