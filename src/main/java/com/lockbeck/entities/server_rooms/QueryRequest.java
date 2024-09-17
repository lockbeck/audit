package com.lockbeck.entities.server_rooms;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QueryRequest {
    private Integer id;
    private String question;
    private String yes;
    private String no;
    private String recommendation;
}
