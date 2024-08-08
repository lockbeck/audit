package com.lockbeck.entities.audit;

import com.lockbeck.entities.auditor.AuditorDTO;
import com.lockbeck.entities.contract.ContractDTO;
import com.lockbeck.entities.file.FileDTO;
import com.lockbeck.entities.letter.LetterDTO;
import com.lockbeck.entities.report.ReportDTO;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class AuditDTO {
    private Integer id;
    private String subject;
    private AuditStatus status;

    private LetterDTO inLetter;
    private LetterDTO outLetter;
    private ContractDTO contract;

    private Double halfPayment;
    private LocalDate halfPaymentDate;
    private LocalDate auditStartDate;

    private AuditorDTO leader;
    private List<AuditorDTO> auditors;

    private FileDTO listDoc;
    private LocalDate auditFinishDate;
    private Double restPayment;
    private LocalDate restPaymentDate;

    private ReportDTO report;
}
