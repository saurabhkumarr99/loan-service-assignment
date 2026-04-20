package com.assignment.loanservice.controller;

import com.assignment.loanservice.dto.ApiResponseDTO;
import com.assignment.loanservice.dto.LoanAuditDTO;
import com.assignment.loanservice.service.LoanAuditService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * REST controller for Loan Audit operations.
 * Exposes endpoints to fetch audit history.
 */
@RestController
@RequestMapping("/loan/audit")
@RequiredArgsConstructor
public class LoanAuditController {

    private final LoanAuditService loanAuditService;

    /**
     * Get all audit logs for loan API calls.
     *
     * @return standardized API response with audit data
     */
    @GetMapping
    public ResponseEntity<ApiResponseDTO<List<LoanAuditDTO>>> getAllAudits() {

        List<LoanAuditDTO> audits = loanAuditService.getAllAuditLogs();

        ApiResponseDTO<List<LoanAuditDTO>> response = ApiResponseDTO.<List<LoanAuditDTO>>builder()
                .status("SUCCESS")
                .message("Audit logs fetched successfully")
                .timestamp(LocalDateTime.now())
                .data(audits)
                .build();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }
}