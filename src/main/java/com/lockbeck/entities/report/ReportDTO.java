package com.lockbeck.entities.report;

import com.lockbeck.entities.file.FileDTO;
import com.lockbeck.entities.letter.LetterDTO;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReportDTO {
    private int id;
    private String number;

    private Integer checkedComps;
    private Integer checkedServers;

    private LetterDTO letter;
    private FileDTO reportFile;

}
