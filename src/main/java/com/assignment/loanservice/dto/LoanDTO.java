package com.assignment.loanservice.dto;

import lombok.Data;

/**
 * Processed loan response.
 */
@Data
public class LoanDTO {

    private String loanAccountNumber;
    private String dueDate;
    private Double emiAmount;
}