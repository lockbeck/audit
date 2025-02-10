package com.lockbeck.entities.subject;

import com.lockbeck.entities.subject.type.SubjectTypeEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "subject")
@Entity
public class SubjectEntity {
    @Id
    private String id;

    private String name;
    private String address;
    private String phone;
    private String email;

    @ManyToOne
    @JoinColumn(name = "subject_type_id",referencedColumnName = "id")
    private SubjectTypeEntity type;

    @PrePersist
    public void prePersist() {
        if (id == null) {
            id = UUID.randomUUID().toString();  // Generates a new UUID when persisting.
        }
    }
    // Getters and Setters
}
