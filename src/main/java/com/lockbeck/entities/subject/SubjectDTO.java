package com.lockbeck.entities.subject;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubjectDTO {
    private String id;
    private String name;
    private String address;
    private String phone;
    private String email;
}
