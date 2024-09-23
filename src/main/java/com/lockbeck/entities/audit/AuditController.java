package com.lockbeck.entities.audit;

import com.lockbeck.demo.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/audit")
@RequiredArgsConstructor
@Validated
@Log4j2
public class AuditController {
    private final AuditService service;
    private final AuditService auditService;

    @PostMapping("/create")
    public ResponseEntity<Response> create(@RequestBody AuditCreateRequest request) {
        return ResponseEntity.ok(service.create(request));
    }

    @PutMapping("/update")
    public ResponseEntity<Response> update(@RequestBody AuditUpdateRequest request) {
        return ResponseEntity.ok(service.update(request));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(service.delete(id));
    }

    @GetMapping("/list")
    public ResponseEntity<Response> list() {
        return ResponseEntity.ok(service.list());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Response> get(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping("/statistics")
    public ResponseEntity<Response> statistics() {
        return ResponseEntity.ok(service.statistics());
    }

    /*@GetMapping("/report/{auditId}")
    public ResponseEntity<?> report(@PathVariable Integer auditId) throws IOException {
        Resource resource = auditService.report(auditId);

        if (resource == null) {
            return new ResponseEntity<>("File not found", HttpStatus.NOT_FOUND);
        }

        String contentType = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
        String headerValue = "inline; filename=\"report.docx\"";

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, headerValue)
                .body(resource);
    }*/
    @GetMapping("/report")
    public ResponseEntity<?> report() throws IOException, InvalidFormatException {
        Resource resource = auditService.report();

        if (resource == null) {
            return new ResponseEntity<>("File not found", HttpStatus.NOT_FOUND);
        }

        String contentType = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
        String headerValue = "inline; filename=\"report.docx\"";

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, headerValue)
                .body(resource);
    }

}
