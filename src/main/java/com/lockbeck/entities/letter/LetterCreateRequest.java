package com.lockbeck.entities.letter;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class LetterCreateRequest {
    private LocalDate date;
    private String number;

    private LocalDate entryDate;
    private String entryNumber;

    private Boolean isOurLetter;

    private Integer stuffId;
    private Integer subjectId;
    private Integer auditorId;

    private String fileId;
}
