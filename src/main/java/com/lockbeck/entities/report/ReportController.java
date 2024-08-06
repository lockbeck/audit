package com.lockbeck.entities.report;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/report")
@RequiredArgsConstructor
@Validated
@Log4j2
public class ReportController {
    private final ReportService service;
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody ReportCreateRequest request){
        return ResponseEntity.ok(service.create(request));
    }
    @GetMapping("/list")
    public ResponseEntity<?> list(){
        return ResponseEntity.ok(service.list());
    }
}
