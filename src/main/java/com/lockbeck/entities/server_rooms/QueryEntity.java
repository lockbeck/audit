package com.lockbeck.entities.server_rooms;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "query")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QueryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "TEXT")
    private String question;
    @Column(columnDefinition = "TEXT")
    private String yes;
    @Column(columnDefinition = "TEXT")
    private String no;
    @Column(columnDefinition = "TEXT")
    private String recommendation;
}
