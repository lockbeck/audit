package com.lockbeck.entities.server_rooms;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "server_rooms")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QueryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String question;
    private String yes;
    private String no;
    private String recommendation;
}
