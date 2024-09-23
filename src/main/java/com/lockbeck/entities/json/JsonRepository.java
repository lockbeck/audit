package com.lockbeck.entities.json;

import com.lockbeck.entities.json.usb.USB;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JsonRepository extends JpaRepository<JsonEntity,Integer> {
    List<JsonEntity> findAllByAuditId(Integer id);

    Optional<JsonEntity> findByAuditIdAndMac(Integer auditId, String mac);
}
