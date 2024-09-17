package com.lockbeck.entities.server_rooms;

import com.lockbeck.demo.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/query")
@RequiredArgsConstructor
public class QueryController {

    private final QueryService service;

    @PostMapping("/create")
    ResponseEntity<Response> create(@RequestBody QueryRequest request) {
        return ResponseEntity.ok(service.create(request));
    }

    @PutMapping("/update")
    ResponseEntity<Response> update(@RequestBody QueryRequest request) {
        return ResponseEntity.ok(service.update(request));
    }

    @GetMapping("/list")
    ResponseEntity<Response> list() {
        return ResponseEntity.ok(service.getList());
    }
    @GetMapping("/get/{id}")
    ResponseEntity<Response> get(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @DeleteMapping("/delete/{id}")
    ResponseEntity<Response> delete(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(service.delete(id));
    }
}
