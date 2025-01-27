package com.lockbeck.entities.json.usb;

import com.lockbeck.entities.json.antivirus.Antivirus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UsbRepository extends JpaRepository<USB,Integer> {
    List<USB> findByJsonId(Integer jsonId);
}
