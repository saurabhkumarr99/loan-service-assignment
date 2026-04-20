package com.assignment.loanservice.service.impl;

import com.assignment.loanservice.dto.EmiDetailDTO;
import com.assignment.loanservice.dto.LoanApiResponseDTO;
import com.assignment.loanservice.dto.LoanDTO;
import com.assignment.loanservice.integration.LoanApiClient;
import com.assignment.loanservice.service.LoanService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoanServiceImpl implements LoanService {

	private static final Logger log = LoggerFactory.getLogger(LoanServiceImpl.class);

	private final LoanApiClient loanApiClient;

	@Override
	public LoanDTO getLoanDetails(String loanAccountNumber) {

		log.info("Fetching loan details for: {}", loanAccountNumber);

		LoanApiResponseDTO apiResponse = loanApiClient.fetchLoanDetails(loanAccountNumber);

		EmiDetailDTO dueEmi = apiResponse.getEmiDetails().stream().filter(EmiDetailDTO::getDueStatus).findFirst()
				.orElse(null);

		LoanDTO response = new LoanDTO();
		response.setLoanAccountNumber(apiResponse.getLoanAccountNumber());

		if (dueEmi != null) {
			response.setDueDate(dueEmi.getMonth());
			response.setEmiAmount(dueEmi.getEmiAmount());
		}

		return response;
	}
}