package com.lockbeck.entities.contract;

import com.lockbeck.entities.file.FileDTO;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class ContractDTO {

    private Integer id;
    private String date;
    private String number;
    private Double price;
    private Integer compNums;
    private Integer serverNums;
    private String objectAddress;
    private FileDTO file;
}
