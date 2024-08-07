package com.lockbeck.entities.stuff;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StuffUpdateDTO {
    private Integer id;
    private String name;
    private String phone;
    private String email;
}
