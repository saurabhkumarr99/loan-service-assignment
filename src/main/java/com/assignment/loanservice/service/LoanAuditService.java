package com.assignment.loanservice.service;

import com.assignment.loanservice.dto.LoanAuditDTO;

import java.util.List;

/**
 * Service for handling loan audit operations.
 * Provides methods to store and retrieve audit logs.
 */
public interface LoanAuditService {

    /**
     * Fetch all loan API audit logs from database.
     *
     * @return list of LoanAuditDTO containing audit history
     */
    List<LoanAuditDTO> getAllAuditLogs();

    /**
     * Fetch audit logs for a specific loan account number.
     *
     * @param loanAccountNumber loan account number
     * @return filtered audit logs for given account
     */
    List<LoanAuditDTO> getAuditByLoanAccountNumber(String loanAccountNumber);
    
    /**
     * Save loan API response into audit table.
     *
     * @param loanAccountNumber loan account number
     * @param responseJson raw API response in JSON format
     */
    void saveAudit(String loanAccountNumber, String responseJson);
}