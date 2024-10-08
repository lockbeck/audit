package com.lockbeck.entities.json;

import com.lockbeck.demo.Response;
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
    public static final String DIRECTORY = System.getProperty("user.home")+"/Desktop/audit/jsons";
    private final JsonService jsonService;

    public JsonController(JsonService jsonService) {
        this.jsonService = jsonService;

    }



    @PostMapping("/upload/{auditId}")
    public ResponseEntity<Response> uploadJsonFiles(@RequestParam("files") MultipartFile[] files, @PathVariable("auditId")Integer auditId) throws IOException {
        List<File> jsonFiles = Arrays.stream(files)
                .map(file -> {
                    try {
                        File tempFile = Files.createTempFile(file.getOriginalFilename(), ".json").toFile();
                        file.transferTo(tempFile);
                        return tempFile;
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }).collect(Collectors.toList());


        return ResponseEntity.ok(jsonService.processJsonFiles(jsonFiles,auditId));
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

}
