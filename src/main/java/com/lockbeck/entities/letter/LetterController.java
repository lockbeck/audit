package com.lockbeck.entities.letter;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/letter")
@RequiredArgsConstructor
@Validated
@Log4j2
public class LetterController {
    private final LetterService service;
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody LetterCreateRequest request){
        return ResponseEntity.ok(service.create(request));
    }

    @GetMapping("/list")
    public ResponseEntity<?> list(){
        return ResponseEntity.ok(service.list());
    }
}
