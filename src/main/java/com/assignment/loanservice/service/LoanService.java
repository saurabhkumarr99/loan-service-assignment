package com.assignment.loanservice.service;

import com.assignment.loanservice.dto.LoanDTO;

/**
 * Service for fetching and processing loan details.
 */
public interface LoanService {

    /**
     * Returns processed loan details for given loan account number.
     */
    LoanDTO getLoanDetails(String loanAccountNumber);
}