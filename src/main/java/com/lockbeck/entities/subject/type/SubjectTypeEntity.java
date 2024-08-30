package com.lockbeck.entities.subject.type;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.validator.constraints.UUID;

@Entity
@Table(name = "subject_type")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubjectTypeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
}
