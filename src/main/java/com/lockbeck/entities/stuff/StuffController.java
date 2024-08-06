package com.lockbeck.entities.stuff;

import com.lockbeck.entities.subject.SubjectCreateRequest;
import com.lockbeck.entities.subject.SubjectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/stuff")
@RequiredArgsConstructor
@Validated
@Log4j2
public class StuffController {
    private final StuffService service;
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody StuffCreateRequest request){
        return ResponseEntity.ok(service.create(request));
    }

    @GetMapping("/list")
    public ResponseEntity<?> list(){
        return ResponseEntity.ok(service.list());
    }
}
