package com.sparta.schedule.controller;

import com.sparta.schedule.dto.FileResponseDto;
import com.sparta.schedule.service.FileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.net.URLConnection;

@RestController
@RequestMapping("/api")
@Tag(name = "Response Estimate File", description = "Response Estimate API File")
public class FileController {

    FileService fileService;
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }
    @PostMapping("/schedules/{id}/upload")
    @Operation(summary = "파일 등록", description = "사용자에게 파일 등록할 때 사용하는 API")
    public FileResponseDto uploadFile(@PathVariable("id") Long scheduleId, @RequestPart("file") MultipartFile file ){
        return fileService.uploadFile(file, scheduleId);
    }

    @GetMapping("/schedules/{id}/download")
    @Operation(summary = "파일 다운로드", description = "사용자에게 파일을 다운로드할 때 사용하는 API")
    public ResponseEntity<Resource> downloadFile(@PathVariable("id") Long scheduleId) throws FileNotFoundException {
        Resource resource = fileService.downloadFile(scheduleId);
        String mimeType = URLConnection.guessContentTypeFromName(resource.getFilename());
        if (mimeType == null) {
            mimeType = "application/octet-stream";
        }
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(mimeType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);

        // 파일 조회 로직

    }


}
