package com.lockbeck.entities.stuff;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StuffCreateRequest {
    private String name;
    private String phone;
    private String email;
}
