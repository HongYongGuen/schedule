package com.sparta.schedule.dto;

import com.sparta.schedule.entity.File;
import lombok.Getter;

@Getter
public class FileResponseDto {
    private Long fileId;
    private String fileName;
    private String filePath;
    private long fileSize;

    public FileResponseDto(File file) {
        this.fileId = file.getFileId();
        this.fileName = file.getFileName();
        this.filePath = file.getFilePath();
        this.fileSize = file.getFileSize();

    }
}
