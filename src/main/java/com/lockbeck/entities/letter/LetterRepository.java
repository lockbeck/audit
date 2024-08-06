package com.lockbeck.entities.letter;

import com.lockbeck.entities.audit.AuditEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LetterRepository extends JpaRepository<LetterEntity,Integer> {
}
