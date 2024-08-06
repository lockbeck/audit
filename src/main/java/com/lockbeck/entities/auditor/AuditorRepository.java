package com.lockbeck.entities.auditor;

import com.lockbeck.entities.audit.AuditEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditorRepository extends JpaRepository<AuditorEntity,Integer> {
}
