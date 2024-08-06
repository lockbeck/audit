package com.lockbeck.entities.contract;

import com.lockbeck.entities.file.FileDTO;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter

public class ContractDTO {

    private Integer id;
    private LocalDate date;
    private String number;
    private Double price;
    private Integer compNums;
    private Integer serverNums;
    private String objectAddress;
    private FileDTO file;
}
