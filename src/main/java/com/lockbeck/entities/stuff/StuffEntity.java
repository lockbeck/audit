package com.lockbeck.entities.stuff;

import com.lockbeck.entities.letter.LetterEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "stuff")
@Entity
public class StuffEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String phone;
    private String email;

    @OneToMany(mappedBy = "stuff")
    private List<LetterEntity> letters;

    // Getters and Setters
}
