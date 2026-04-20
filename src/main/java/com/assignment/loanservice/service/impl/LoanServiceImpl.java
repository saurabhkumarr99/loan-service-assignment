package com.assignment.loanservice.service.impl;

import com.assignment.loanservice.dto.EmiDetailDTO;
import com.assignment.loanservice.dto.LoanApiResponseDTO;
import com.assignment.loanservice.dto.LoanDTO;
import com.assignment.loanservice.dto.LoanDTO.LoanDetail;
import com.assignment.loanservice.integration.LoanApiClient;
import com.assignment.loanservice.service.LoanAuditService;
import com.assignment.loanservice.service.LoanService;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LoanServiceImpl implements LoanService {

	private static final Logger log = LoggerFactory.getLogger(LoanServiceImpl.class);

	private final LoanApiClient loanApiClient;
	private final LoanAuditService loanAuditService;
	private final ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public LoanDTO getLoanDetails(String loanAccountNumber) {

		log.info("Fetching loan details for: {}", loanAccountNumber);

		LoanApiResponseDTO apiResponse = loanApiClient.fetchLoanDetails(loanAccountNumber);

		// save for audit
		try {
			String jsonResponse = objectMapper.writeValueAsString(apiResponse);
			loanAuditService.saveAudit(loanAccountNumber, jsonResponse);
		} catch (Exception e) {
			log.error("Error while saving audit", e);
		}

		// filter all due EMIs
		List<LoanDetail> dueEmis = apiResponse.getEmiDetails().stream().filter(EmiDetailDTO::getDueStatus)
				.map(emi -> {
					LoanDTO.LoanDetail detail = new LoanDTO.LoanDetail();
					detail.setDueDate(emi.getMonth());
					detail.setEmiAmount(emi.getEmiAmount());
					return detail;
				}).collect(Collectors.toList());

		LoanDTO response = new LoanDTO();
		response.setLoanAccountNumber(apiResponse.getLoanAccountNumber());
		response.setLoanDetails(dueEmis);

		return response;
	}
}