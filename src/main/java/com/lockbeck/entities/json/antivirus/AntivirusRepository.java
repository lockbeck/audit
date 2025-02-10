package com.lockbeck.entities.json.antivirus;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AntivirusRepository extends JpaRepository<Antivirus,Integer> {
    List<Antivirus> findAllByJsonId(Integer jsonId);
}
