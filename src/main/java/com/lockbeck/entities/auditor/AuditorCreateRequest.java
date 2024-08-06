package com.lockbeck.entities.auditor;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuditorCreateRequest {
    private String name;
    private String phone;
    private String email;
}
