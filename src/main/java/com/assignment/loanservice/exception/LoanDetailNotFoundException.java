package com.assignment.loanservice.exception;

/**
 * Exception thrown when loan details are not found
 * for a given loan account number.
 */
public class LoanDetailNotFoundException extends RuntimeException {

    public LoanDetailNotFoundException(String message) {
        super(message);
    }
}