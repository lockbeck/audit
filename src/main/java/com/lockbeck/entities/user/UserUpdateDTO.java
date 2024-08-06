package com.lockbeck.entities.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateDTO {
    private Integer id;
    private String username;
    private String name;
    private String email;
    private Role role;
}
