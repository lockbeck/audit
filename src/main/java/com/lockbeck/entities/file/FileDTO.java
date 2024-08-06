package com.lockbeck.entities.file;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)

public class FileDTO {
    private String fileId;
    private String originalName;
    private String extension;
    private Long size;
    private String folderPath;
    private String downloadUrl;

    private LocalDateTime createdDate;




}
