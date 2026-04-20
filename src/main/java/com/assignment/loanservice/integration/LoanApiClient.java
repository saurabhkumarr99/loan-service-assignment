package com.assignment.loanservice.integration;

import com.assignment.loanservice.dto.LoanApiResponseDTO;
import com.assignment.loanservice.exception.LoanDetailNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

/**
 * Client to call external loan API.
 */
@Component
@RequiredArgsConstructor
public class LoanApiClient {

	private static final Logger log = LoggerFactory.getLogger(LoanApiClient.class);

	private final RestTemplate restTemplate;

	@Value("${loan.api.base-url}")
	private String baseUrl;

	public LoanApiResponseDTO fetchLoanDetails(String loanId) {

		String url = baseUrl + loanId;

		try {
			log.info("Calling external API for loanId: {}", loanId);

			ResponseEntity<LoanApiResponseDTO> response = restTemplate.exchange(url, HttpMethod.GET, null,
					LoanApiResponseDTO.class);

			log.debug("External API success response: {}", response.getBody());
			return response.getBody();

		} catch (HttpClientErrorException.NotFound ex) {

			// Handle 404 - if loan not found
			log.warn("Loan not found (404) for loanId: {}", loanId);

			throw new LoanDetailNotFoundException("Loan not found for account: " + loanId);
		} catch (Exception ex) {

			log.error("Error calling external API for loanId: {}", loanId, ex.getLocalizedMessage());

			throw new LoanDetailNotFoundException("Failed to fetch loan details for account: " + loanId);
		}
	}
}