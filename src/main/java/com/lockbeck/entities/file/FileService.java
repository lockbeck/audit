package com.lockbeck.entities.file;

import com.lockbeck.exceptions.BadRequestException;
import com.lockbeck.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Calendar;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FileService {
    @Value("${upload.folder}")
    private String uploadFolder; //  uploads/
    @Value("${file.dwnUrl}")
    private String fileDownloadUrl;
    private final FileRepository fileRepository;
    public FileEntity get(String fileId){
        Optional<FileEntity> byId = fileRepository.findById(fileId);
        if (byId.isEmpty()) {
            throw new NotFoundException("fayl topilmadi");
        }
        return byId.get();
    }

    public FileEntity save(MultipartFile multipartFile) throws IOException {
        if (isHazardousFile(multipartFile.getOriginalFilename())) {
            throw new BadRequestException("Ushbu file zararli bolishi mumkin iltimos pdf jpeg jpg yoki png file yuklang: " + multipartFile.getName());
        }
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        long size = multipartFile.getSize();

        String ymDString = getYmDString();
        Path uploadPath = Paths.get(uploadFolder+ymDString);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        FileEntity entity = new  FileEntity();
        entity.setOriginalName(fileName);
        entity.setExtension(getExtension(fileName));
        entity.setSize(size);
        entity.setPath(uploadPath.toString());
        entity.setDownloadUrl(fileDownloadUrl);
        FileEntity file = fileRepository.save(entity);

        try (InputStream inputStream = multipartFile.getInputStream()) {
            Path filePath = uploadPath.resolve(file.getId()+"."+file.getExtension());
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ioe) {
            throw new IOException("Could not save file: " + fileName, ioe);
        }

        return file;

//        FileUploadResponse response = new FileUploadResponse();
//        response.setId(fileCode);
//        response.setFileName(fileName);
//        response.setSize(size);
//        response.setDownloadUri("/file/downloadFile/" + fileCode);
//
//        return response;

    }
    private boolean isHazardousFile(String fileName) {
        // Define hazardous file extensions
        String[] hazardousExtensions = {".exe", ".bat", ".vbs", ".js",
                ".jar", ".msi", ".com", ".cmd", ".scr",
        ".ps1",".zip",".rar",".tar",".docm",".xlsm",".lnk",".html",".htm",
                ".mp4", ".avi", ".mkv", ".mov", ".wmv", ".flv", ".webm",
                ".mp3", ".wav", ".flac", ".aac", ".ogg", ".wma"};
        String fileExtension = "."+getExtension(fileName);

        // Check if the file extension is hazardous
        for (String hazardousExtension : hazardousExtensions) {
            if (hazardousExtension.equalsIgnoreCase(fileExtension)) {
                return true;
            }
        }
        return false;
    }


    public Resource getFileAsResource(String id) throws IOException {
 
        Optional<FileEntity> byId = fileRepository.findById(id);
        String path = byId.get().getPath();

        final Path[] foundFile = new Path[1];
        Path dirPath = Paths.get(path);

            Files.list(dirPath).forEach(file -> {
                if (file.getFileName().toString().startsWith(id)) {
                    foundFile[0] = file;
                }
            });

            if (foundFile[0] != null) {
                return new UrlResource(foundFile[0].toUri());
            }

            return null;

    }


    private String getExtension(String fileName) { // mp3/jpg/npg/mp4.....
        int lastIndex = fileName.lastIndexOf(".");
        return fileName.substring(lastIndex + 1);
    }
    private String getFileName(String fileName) { // mp3/jpg/npg/mp4.....
        int lastIndex = fileName.lastIndexOf(".");
        return fileName.substring(lastIndex);
    }

    public String getYmDString() {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
        int day = Calendar.getInstance().get(Calendar.DATE);

        return "/"+year + "/" + month + "/" + day; // 2022/04/23
    }
    public FileEntity getFile(String fileCode){
        Optional<FileEntity> byId = fileRepository.findById(fileCode);
        if(byId.isEmpty()){
            throw new NotFoundException("File could not be found");
        }
        return byId.get();
    }


    public FileDTO getFileDto(FileEntity file) {
        if (file==null) {
            return null;
        }
        return FileDTO.builder()
                .fileId(file.getId())
                .downloadUrl(file.getDownloadUrl()+file.getId())
                .originalName(file.getOriginalName())
                .build();
    }
}
