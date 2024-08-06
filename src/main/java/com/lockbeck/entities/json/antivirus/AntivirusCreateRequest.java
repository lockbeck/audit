package com.lockbeck.entities.json.antivirus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AntivirusCreateRequest {
    public String Name;

    public String Status;

    public String UpdatedAt;
}
