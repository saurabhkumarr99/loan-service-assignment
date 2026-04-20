package com.assignment.loanservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.assignment.loanservice.model.LoanAudit;

/**
 * Repository for Loan Audit table.
 */
@Repository
public interface LoanAuditRepository extends JpaRepository<LoanAudit, Long> {

	/**
	 * Fetch audit logs by loan account number.
	 */
	List<LoanAudit> findByLoanAccountNumber(String loanAccountNumber);
}