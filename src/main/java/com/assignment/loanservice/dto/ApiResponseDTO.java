package com.assignment.loanservice.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Generic wrapper to standardize API responses.
 */
@Data
@Builder
public class ApiResponseDTO<T> {

    private String status;          
    private String message;       
    private LocalDateTime timestamp; 
    private T data;                 
}