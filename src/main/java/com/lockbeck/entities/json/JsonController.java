package com.lockbeck.entities.json;

import com.lockbeck.demo.Response;
import com.lockbeck.exceptions.BadRequestException;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/json")
public class JsonController {
    private final JsonService jsonService;

    public JsonController(JsonService jsonService) {
        this.jsonService = jsonService;

    }



    @PostMapping("/upload/{auditId}")
    public ResponseEntity<Response> uploadJsonFiles(@RequestParam("files") MultipartFile[] files, @PathVariable("auditId")Integer auditId)  {
        List<File> jsonFiles = Arrays.stream(files)
                .map(file -> {
                    try {
                        File tempFile = Files.createTempFile(file.getOriginalFilename(), ".json").toFile();
                        file.transferTo(tempFile);
                        return tempFile;
                    } catch (IOException e) {
                        System.out.println(e.getMessage()+"controllerda try catchga tushdi");
                        throw new BadRequestException("controllerda try catchga tushdi");
                    }
                }).collect(Collectors.toList());


        return ResponseEntity.ok(jsonService.processJsonFiles(jsonFiles,auditId));
    }

    @PostMapping("/create/{auditId}")
    public ResponseEntity<Response> create(@RequestBody JsonCreateRequest dto,@PathVariable("auditId")Integer auditId) {
        return ResponseEntity.ok(jsonService.create(dto,auditId));
    }

    @PostMapping("/update/{jsonId}")
    public ResponseEntity<Response> update(@RequestBody JsonUpdateRequest dto,@PathVariable("jsonId")Integer auditId) {
        return ResponseEntity.ok(jsonService.update(dto,auditId));
    }



    @GetMapping("/report/{auditId}")
    public ResponseEntity<?> downloadFile(@PathVariable("auditId")Integer auditId) throws IOException {

        Resource resource = jsonService.createWordReport(auditId);

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

    @GetMapping("/list/{auditId}")
    public ResponseEntity<?> list(@PathVariable("auditId")Integer auditId) throws IOException {

      return ResponseEntity.ok(jsonService.list(auditId));

    }

    @DeleteMapping("/delete/{jsonId}")
    public ResponseEntity<Response> delete(@PathVariable("jsonId")Integer auditId) throws IOException {
        return ResponseEntity.ok(jsonService.delete(auditId));
    }

}
