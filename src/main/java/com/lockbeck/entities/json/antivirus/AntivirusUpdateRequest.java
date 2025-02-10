package com.lockbeck.entities.json.antivirus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AntivirusUpdateRequest {
    private Integer id;

    public String name;

    public String status;

    public String updatedAt;
}
