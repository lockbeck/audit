package com.lockbeck.entities.json;

import com.lockbeck.config.JwtService;
import com.lockbeck.entities.user.UserEntity;
import com.lockbeck.entities.user.UserService;
import com.lockbeck.exceptions.BadRequestException;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.nio.file.Files.copy;
import static java.nio.file.Paths.get;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

@RestController
@RequestMapping("/json")
public class JsonController {
    public static final String DIRECTORY = System.getProperty("user.home")+"/Desktop/audit/jsons";
    private final JsonService jsonService;
    private final JwtService jwtService;
    private final UserService userService;

    public JsonController(JsonService jsonService, JwtService jwtService, UserService userService) {
        this.jsonService = jsonService;
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @PostMapping("/upload")
    public ResponseEntity<List<String>> uploadFiles(@RequestParam("file") List<MultipartFile> files) throws IOException {
        List<String> response = new ArrayList<>();
        for (MultipartFile file : files) {
            String filename = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
            Path fileStorage = get(DIRECTORY,filename).toAbsolutePath().normalize();
            copy(file.getInputStream(),fileStorage,REPLACE_EXISTING);
            response.add(filename);
        }
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/upload-json/{auditId}")
    public String uploadJsonFiles(@RequestParam("files") MultipartFile[] files, @PathVariable("auditId")Integer auditId) throws IOException {
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

        jsonService.processJsonFiles(jsonFiles,auditId);

        return "Files processed successfully";
    }

    @GetMapping("/report")
    public ResponseEntity<?> downloadFile() throws IOException {

        Resource resource = jsonService.createWordReport();

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
