package com.lockbeck.entities.auditor;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auditor")
@RequiredArgsConstructor
@Validated
@Log4j2
public class AuditorController {
    private final AuditorService service;
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody AuditorCreateRequest request){
        return ResponseEntity.ok(service.create(request));
    }

    @GetMapping("/list")
    public ResponseEntity<?> list(){
        return ResponseEntity.ok(service.list());
    }

    @GetMapping("/getAudits/{id}")
    public ResponseEntity<?> get(@PathVariable Integer id){
        return ResponseEntity.ok(service.getAudits(id));
    }
}
