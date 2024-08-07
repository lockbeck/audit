package com.lockbeck.entities.stuff;

import com.lockbeck.demo.Response;
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
    public ResponseEntity<Response> create(@RequestBody StuffCreateRequest request){
        return ResponseEntity.ok(service.create(request));
    }

    @PutMapping("/update")
    public ResponseEntity<Response> update(@RequestBody StuffUpdateDTO request){
        return ResponseEntity.ok(service.update(request));
    }

    @GetMapping("/list")
    public ResponseEntity<Response> list(){
        return ResponseEntity.ok(service.list());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Response> get(@PathVariable Integer id){
        return ResponseEntity.ok(service.getById(id));
    }
}
