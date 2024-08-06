package com.lockbeck.entities.audit;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/audit")
@RequiredArgsConstructor
@Validated
@Log4j2
public class AuditController {
    private final AuditService service;
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody AuditCreateRequest request){
        return ResponseEntity.ok(service.create(request));
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody AuditUpdateRequest request){
        return ResponseEntity.ok(service.update(request));
    }




}
