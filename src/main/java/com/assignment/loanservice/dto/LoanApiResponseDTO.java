package com.assignment.loanservice.dto;

import lombok.Data;

import java.util.List;

/**
 * External API response mapping.
 */
@Data
public class LoanApiResponseDTO {

    private String loanAccountNumber;
    private List<EmiDetailDTO> emiDetails;
}