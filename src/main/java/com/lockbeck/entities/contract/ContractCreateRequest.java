package com.lockbeck.entities.contract;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ContractCreateRequest {

    private String date;
    private String number;
    private Double price;
    private Integer compNums;
    private Integer serverNums;
    private String objectAddress;
    private String fileId;
}
