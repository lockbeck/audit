package com.lockbeck.entities.letter;

import com.lockbeck.entities.auditor.AuditorEntity;
import com.lockbeck.entities.file.FileEntity;
import com.lockbeck.entities.stuff.StuffEntity;
import com.lockbeck.entities.subject.SubjectEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "letter")
@Entity
public class LetterEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private LocalDate date;
    private String number;
    private LocalDate entryDate;
    private String entryNumber;
    private Boolean isOurLetter;

    @ManyToOne
    @JoinColumn(name = "stuff_id")
    private StuffEntity stuff;

    @ManyToOne
    @JoinColumn(name = "subject_id")
    private SubjectEntity subject;

    @ManyToOne
    @JoinColumn(name = "auditor_id")
    private AuditorEntity auditor;

    @ManyToOne
    @JoinColumn(name = "file_id")
    private FileEntity file;
}
