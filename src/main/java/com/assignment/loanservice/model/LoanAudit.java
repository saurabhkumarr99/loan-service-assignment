package com.assignment.loanservice.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Audit table to store all loan API responses.
 */
@Entity
@Table(name = "loan_audit")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoanAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String loanAccountNumber;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String responseJson;

    private LocalDateTime createdAt;
}