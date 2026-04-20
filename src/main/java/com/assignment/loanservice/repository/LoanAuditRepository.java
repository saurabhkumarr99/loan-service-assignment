package com.assignment.loanservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.assignment.loanservice.model.LoanAudit;

/**
 * Repository for Loan Audit table.
 */
@Repository
public interface LoanAuditRepository extends JpaRepository<LoanAudit, Long> {
}