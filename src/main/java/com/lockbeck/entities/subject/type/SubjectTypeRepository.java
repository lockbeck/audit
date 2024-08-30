package com.lockbeck.entities.subject.type;

import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.*;
import java.util.Optional;

public interface SubjectTypeRepository extends JpaRepository<SubjectTypeEntity, Integer> {
    Optional<SubjectTypeEntity> findByName(String type);
}
