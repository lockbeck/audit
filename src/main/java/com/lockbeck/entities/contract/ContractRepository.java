package com.lockbeck.entities.contract;

import com.lockbeck.entities.audit.AuditEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContractRepository extends JpaRepository<ContractEntity,Integer> {
}
