package com.assignment.loanservice.integration;

import com.assignment.loanservice.dto.LoanApiResponseDTO;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * Client to call external loan API.
 */
@Component
@RequiredArgsConstructor
public class LoanApiClient {

	private static final Logger log = LoggerFactory.getLogger(LoanApiClient.class);

	private final RestTemplate restTemplate;

	public LoanApiResponseDTO fetchLoanDetails(String loanId) {

		String url = "https://demo9993930.mockable.io/loanaccount/" + loanId;

		log.info("Calling external API for loanId: {}", loanId);

		LoanApiResponseDTO response = restTemplate.getForObject(url, LoanApiResponseDTO.class);

		log.debug("Received response from external API: {}", response);

		return response;
	}
}