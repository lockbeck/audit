package com.lockbeck.entities.subject.type;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
    private String id;
    private String name;
}
