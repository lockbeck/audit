package com.lockbeck.entities.json;

import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.nio.file.Files.copy;
import static java.nio.file.Paths.get;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

@RestController
@RequestMapping("/json")
public class JsonController {
    public static final String DIRECTORY = System.getProperty("user.home")+"/Desktop/audit/jsons";
    private final JsonService jsonService;

    public JsonController(JsonService jsonService) {
        this.jsonService = jsonService;
    }

    @PostMapping("/upload")
    public ResponseEntity<List<String>> uploadFiles(@RequestParam("file") List<MultipartFile> files) throws IOException {
        List<String> response = new ArrayList<>();
        for (MultipartFile file : files) {
            String filename = StringUtils.cleanPath(file.getOriginalFilename());
            Path fileStorage = get(DIRECTORY,filename).toAbsolutePath().normalize();
            copy(file.getInputStream(),fileStorage,REPLACE_EXISTING);
            response.add(filename);
        }
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/upload-json")
    public String uploadJsonFiles(@RequestParam("files") MultipartFile[] files) throws IOException {
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

        jsonService.processJsonFiles(jsonFiles);

        return "Files processed successfully";
    }

}
