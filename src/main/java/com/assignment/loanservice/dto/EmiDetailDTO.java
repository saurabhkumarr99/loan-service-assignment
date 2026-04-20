package com.assignment.loanservice.dto;

import lombok.Data;

/**
 * Represents EMI details from external API.
 */
@Data
public class EmiDetailDTO {

    private String month;
    private Double emiAmount;
    private Boolean paidStatus;
    private Boolean dueStatus;
}