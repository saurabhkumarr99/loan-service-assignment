package com.assignment.loanservice.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Standard error response.
 */
@Data
@Builder
public class ErrorResponseDTO {

    private String status;
    private String message;
    private LocalDateTime timestamp;
}