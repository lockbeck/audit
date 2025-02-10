package com.lockbeck.entities.json.usb;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UsbRepository extends JpaRepository<USB,Integer> {

    List<USB> findAllByJsonId(Integer jsonId);
}
