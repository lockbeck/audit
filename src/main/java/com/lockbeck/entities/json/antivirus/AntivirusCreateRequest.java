package com.lockbeck.entities.json.antivirus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AntivirusCreateRequest {
    public String name;

    public String status;

    public String updatedAt;
}
