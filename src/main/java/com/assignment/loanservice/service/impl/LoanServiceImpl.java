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

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LoanServiceImpl implements LoanService {

    private static final Logger log = LoggerFactory.getLogger(LoanServiceImpl.class);

    private final LoanApiClient loanApiClient;

    @Override
    public LoanDTO getLoanDetails(String loanAccountNumber) {

        log.info("Fetching loan details for: {}", loanAccountNumber);

        LoanApiResponseDTO apiResponse =
                loanApiClient.fetchLoanDetails(loanAccountNumber);

        //filter all due EMIs
        List<LoanDTO.LoanDetail> dueEmis = apiResponse.getEmiDetails()
                .stream()
                .filter(EmiDetailDTO::getDueStatus)
                .map(emi -> {
                    LoanDTO.LoanDetail detail = new LoanDTO.LoanDetail();
                    detail.setDueDate(emi.getMonth());
                    detail.setEmiAmount(emi.getEmiAmount());
                    return detail;
                })
                .collect(Collectors.toList());

        LoanDTO response = new LoanDTO();
        response.setLoanAccountNumber(apiResponse.getLoanAccountNumber());
        response.setLoanDetails(dueEmis);

        return response;
    }
}