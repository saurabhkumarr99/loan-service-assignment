package com.assignment.loanservice.service.impl;

import com.assignment.loanservice.dto.LoanAuditDTO;
import com.assignment.loanservice.exception.LoanDetailNotFoundException;
import com.assignment.loanservice.model.LoanAudit;
import com.assignment.loanservice.repository.LoanAuditRepository;
import com.assignment.loanservice.service.LoanAuditService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of LoanAuditService. Handles saving and fetching audit logs
 * from database.
 */
@Service
@RequiredArgsConstructor
public class LoanAuditServiceImpl implements LoanAuditService {

	private static final Logger log = LoggerFactory.getLogger(LoanAuditServiceImpl.class);

	private final LoanAuditRepository loanAuditRepository;

	/**
	 * Save loan API response into audit table.
	 */
	@Override
	public void saveAudit(String loanAccountNumber, String responseJson) {

		log.info("Saving audit for loanAccountNumber: {}", loanAccountNumber);

		LoanAudit audit = LoanAudit.builder().loanAccountNumber(loanAccountNumber).responseJson(responseJson)
				.createdAt(LocalDateTime.now()).build();

		loanAuditRepository.save(audit);

		log.info("Audit saved successfully for loanAccountNumber: {}", loanAccountNumber);
	}

	/**
	 * Fetch all audit logs from database and convert to DTO.
	 */
	@Override
	public List<LoanAuditDTO> getAllAuditLogs() {

		log.info("Fetching all loan audit logs");

		return loanAuditRepository.findAll().stream()
				.map(audit -> LoanAuditDTO.builder().id(audit.getId()).loanAccountNumber(audit.getLoanAccountNumber())
						.responseJson(audit.getResponseJson()).createdAt(audit.getCreatedAt()).build())
				.collect(Collectors.toList());
	}

	@Override
	public List<LoanAuditDTO> getAuditByLoanAccountNumber(String loanAccountNumber) {

		log.info("Fetching audit logs for loanAccountNumber: {}", loanAccountNumber);

		List<LoanAuditDTO> audits = loanAuditRepository.findByLoanAccountNumber(loanAccountNumber).stream()
				.map(audit -> LoanAuditDTO.builder().id(audit.getId()).loanAccountNumber(audit.getLoanAccountNumber())
						.responseJson(audit.getResponseJson()).createdAt(audit.getCreatedAt()).build())
				.collect(Collectors.toList());

		// If no data found, throw exception
		if (audits.isEmpty()) {
			throw new LoanDetailNotFoundException("No audit records found for loanAccountNumber: " + loanAccountNumber);
		}

		return audits;
	}
}