package com.lockbeck.entities.report;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReportUpdateRequest {
    private int id;
    private String number;

    private Integer checkedComps;
    private Integer checkedServers;

    private Integer letterId;
    private String reportFileId;
}
