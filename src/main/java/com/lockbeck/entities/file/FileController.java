package com.lockbeck.entities.file;


import com.lockbeck.config.JwtService;
import com.lockbeck.demo.Response;
import com.lockbeck.entities.user.UserEntity;
import com.lockbeck.entities.user.UserService;
import com.lockbeck.exceptions.BadRequestException;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/file")
@PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
@RequiredArgsConstructor
public class
FileController {
    private final FileService fileService;
    private final JwtService jwtService;
    private final UserService userService;

    @PostMapping("/uploadFile")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    @Hidden
    public ResponseEntity<Response> uploadFile(
            @RequestParam("file") MultipartFile multipartFile)
            throws IOException {





        return  ResponseEntity.ok(fileService.save(multipartFile));
    }

    @GetMapping("/downloadFile/{fileCode}")
    @PreAuthorize("permitAll()")
    public ResponseEntity<?> downloadFile(@PathVariable("fileCode") String fileCode,
                                          @RequestParam("token") String token) throws IOException {

        Resource resource = fileService.getFileAsResource(fileCode);

        String username = jwtService.extractUsername(token);

        UserEntity byUsername = userService.getByUsername(username);
        if (!jwtService.isTokenValid(token,byUsername)) {
            throw new BadRequestException("Invalid Token");
        }

        if (resource == null) {
            return new ResponseEntity<>("File not found", HttpStatus.NOT_FOUND);
        }

        String contentType;
        if (resource.getFilename().toLowerCase().endsWith(".jpeg") || resource.getFilename().toLowerCase().endsWith(".jpg")) {
            contentType = "image/jpeg";
        } else if (resource.getFilename().toLowerCase().endsWith(".png")) {
            contentType = "image/png";
        }
        else if(resource.getFilename().toLowerCase().endsWith(".pdf")){
            contentType="application/pdf";
        }
        else if (resource.getFilename().toLowerCase().endsWith(".docx")) {
            contentType = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
        } else if (resource.getFilename().toLowerCase().endsWith(".doc")) {
            contentType = "application/msword";
        }
        else if (resource.getFilename().toLowerCase().endsWith(".xlsx")) {
            contentType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
        } else if (resource.getFilename().toLowerCase().endsWith(".xls")) {
            contentType = "application/vnd.ms-excel";
        }
        else if (resource.getFilename().toLowerCase().endsWith(".avif")) {
            contentType = "image/avif";
        }
        else {
            contentType = "application/octet-stream"; // Default to octet-stream for other file types
        }

        String headerValue = "inline; filename=\"" + resource.getFilename() + "\"";

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, headerValue)
                .body(resource);
    }
}