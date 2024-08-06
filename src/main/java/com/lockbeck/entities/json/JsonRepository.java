package com.lockbeck.entities.json;

import com.lockbeck.entities.json.usb.USB;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JsonRepository extends JpaRepository<JsonEntity,Integer> {
}
