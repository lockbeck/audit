package com.lockbeck.entities.contract;

import com.lockbeck.entities.audit.AuditEntity;
import com.lockbeck.entities.file.FileEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "contract")
@Entity
public class ContractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private LocalDate date;
    private String number;
    private Double price;
    private Integer compNums;
    private Integer serverNums;
    private String objectAddress;

    @OneToOne(mappedBy = "contract")
    AuditEntity audit;

    @ManyToOne
    @JoinColumn(name = "file_id")
    private FileEntity file;
}
