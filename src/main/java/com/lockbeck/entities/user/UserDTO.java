package com.lockbeck.entities.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {
    private Integer id;

    private String name;
    private String username;
    private String email;
    private String password;
    private String confirmPsw;
    private Role role;
    private LocalDateTime createdDate;
    private Boolean online;
    private String lastOnlineDate;
}
