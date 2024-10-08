package com.lockbeck.entities.subject;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubjectUpdateDTO {
    private String id;
    private String name;
    private String address;
    private String phone;
    private String email;
}
