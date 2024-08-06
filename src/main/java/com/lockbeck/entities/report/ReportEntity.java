package com.lockbeck.entities.report;

import com.lockbeck.entities.file.FileEntity;
import com.lockbeck.entities.letter.LetterEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "report")
@Entity
public class ReportEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String number;

    private Integer checkedComps;
    private Integer checkedServers;

    @ManyToOne
    @JoinColumn(name = "letter_id")
    private LetterEntity letter;

    @ManyToOne
    @JoinColumn(name = "report_file_id")
    private FileEntity reportFile;

    // Getters and Setters
}
