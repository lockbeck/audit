package com.lockbeck.entities.report;

import com.lockbeck.entities.audit.AuditEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<ReportEntity,Integer> {
}
