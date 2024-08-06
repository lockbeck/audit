package com.lockbeck.entities.stuff;

import com.lockbeck.entities.audit.AuditEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StuffRepository extends JpaRepository<StuffEntity,Integer> {
}
