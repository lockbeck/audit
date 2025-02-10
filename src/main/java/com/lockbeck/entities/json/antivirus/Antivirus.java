package com.lockbeck.entities.json.antivirus;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.lockbeck.entities.json.JsonEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "antivirus")
@Entity
public class Antivirus{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    private String name;

    private String status;

    private String updatedAt;

    @ManyToOne
    @JoinColumn(name = "json_id")
    @JsonBackReference
    private JsonEntity json;



}