package com.lockbeck.entities.json.antivirus;

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

    public String name;

    public String status;

    public String updatedAt;

    @ManyToOne
    @JoinColumn(name = "json_id")
    private JsonEntity json;



}