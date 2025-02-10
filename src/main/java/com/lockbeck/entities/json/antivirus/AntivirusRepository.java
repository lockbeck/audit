package com.lockbeck.entities.json.antivirus;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AntivirusRepository extends JpaRepository<Antivirus,Integer> {
    List<Antivirus> findAllByJsonId(Integer jsonId);
}
