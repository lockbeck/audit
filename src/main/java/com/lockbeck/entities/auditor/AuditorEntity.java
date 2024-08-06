package com.lockbeck.entities.auditor;

import com.lockbeck.entities.audit.AuditEntity;
import com.lockbeck.entities.letter.LetterEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "auditor")
@Entity
public class AuditorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String phone;
    private String email;

    @OneToMany(mappedBy = "leader")
    private List<AuditEntity> ledAudits;

    @OneToMany(mappedBy = "auditor")
    private List<LetterEntity> letters;


    @ManyToMany(mappedBy = "auditors")
    private Set<AuditEntity> audits = new HashSet<>();


}
