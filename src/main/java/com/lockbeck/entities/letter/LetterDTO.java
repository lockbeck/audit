package com.lockbeck.entities.letter;

import com.lockbeck.entities.auditor.AuditorDTO;
import com.lockbeck.entities.file.FileDTO;
import com.lockbeck.entities.stuff.StuffDTO;
import com.lockbeck.entities.subject.SubjectDTO;
import lombok.Getter;
import lombok.Setter;

import javax.security.auth.Subject;
import java.time.LocalDate;

@Getter
@Setter
public class LetterDTO {
    private Integer id;

    private LocalDate date;
    private String number;
    private LocalDate entryDate;
    private String entryNumber;
    private Boolean isOurLetter;

    private StuffDTO stuff;
    private SubjectDTO subject;
    private AuditorDTO auditor;
    private FileDTO file;

}
