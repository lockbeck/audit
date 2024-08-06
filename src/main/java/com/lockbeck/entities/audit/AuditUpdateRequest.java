package com.lockbeck.entities.audit;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class AuditUpdateRequest {
    private Integer id;
    private Integer outLetterId;
    private Integer contractId;

    private Double halfPayment;
    private LocalDate halfPaymentDate;

    private LocalDate auditStartDate;

    private Integer leaderId;
    private List<Integer> auditorIds;
    private String listDocId;

    private LocalDate auditFinishDate;
    private Double restPayment;
    private LocalDate restPaymentDate;
    private Integer reportId;
}
