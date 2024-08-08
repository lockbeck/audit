package com.lockbeck.entities.audit;

import com.lockbeck.entities.auditor.AuditorEntity;
import com.lockbeck.entities.contract.ContractEntity;
import com.lockbeck.entities.file.FileEntity;
import com.lockbeck.entities.letter.LetterEntity;
import com.lockbeck.entities.report.ReportEntity;
import com.lockbeck.entities.subject.SubjectEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "audit")
@Entity
public class AuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @Enumerated(EnumType.STRING)
    private AuditStatus status;

    @ManyToOne
    @JoinColumn(name = "in_letter_id")
    private LetterEntity inLetter;

    @ManyToOne
    @JoinColumn(name = "out_letter_id")
    private LetterEntity outLetter;

    @OneToOne
    @JoinColumn(name = "contract_id")
    private ContractEntity contract;

    private Double halfPayment;
    private LocalDate halfPaymentDate;
    private LocalDate auditStartDate;

    @ManyToOne
    @JoinColumn(name = "leader_id")
    private AuditorEntity leader;

    @ManyToOne
    @JoinColumn(name = "list_doc_id")
    private FileEntity listDoc;
    private LocalDate auditFinishDate;
    private Double restPayment;
    private LocalDate restPaymentDate;

    @ManyToOne
    @JoinColumn(name = "report_id")
    private ReportEntity report;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "audit_auditor",
            joinColumns = {@JoinColumn(name = "audit_id")},
            inverseJoinColumns = {@JoinColumn(name = "auditor_id")}
    )
    Set<AuditorEntity> auditors = new HashSet<>();
}
