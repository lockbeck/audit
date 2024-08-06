package com.lockbeck.entities.auditor;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuditorUpdateRequest {
    private Integer id;
    private String name;
    private String phone;
    private String email;
}
