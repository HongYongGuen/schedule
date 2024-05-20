package com.sparta.schedule.service;

import com.sparta.schedule.dto.FileRequestDto;
import com.sparta.schedule.dto.FileResponseDto;
import com.sparta.schedule.entity.File;
import com.sparta.schedule.entity.Schedule;
import com.sparta.schedule.exception.FileException;
import com.sparta.schedule.exception.ScheduleNotFoundException;
import com.sparta.schedule.repository.FileRepository;
import com.sparta.schedule.repository.ScheduleRepository;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Service
public class FileService {

    private final ScheduleService scheduleService;
    private final FileRepository fileRepository;

    private final ScheduleRepository scheduleRepository;
    public FileService(ScheduleService scheduleService, FileRepository fileRepository, ScheduleRepository scheduleRepository) {
        this.scheduleService = scheduleService;
        this.fileRepository = fileRepository;
        this.scheduleRepository = scheduleRepository;
    }

    public FileResponseDto uploadFile(MultipartFile file, Long scheduleId) {
        try {
            Optional<Schedule> result = scheduleService.findScheduleById(scheduleId);
            if (result.isEmpty()) throw new ScheduleNotFoundException("선택된 일정이 없습니다.");

            Path uploadPath = Paths.get("images");
            // 디렉토리가 존재하지 않으면 생성
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            File savedFile = savedFile(file,uploadPath);
            // 해당 일정에 파일 연결
            result.get().setFileId(savedFile.getFileId());
            scheduleRepository.save(result.get());
            return new FileResponseDto(savedFile);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private File savedFile(MultipartFile file,  Path uploadPath) throws IOException {
        String fileName = file.getOriginalFilename();
        String filePath = uploadPath.resolve(fileName).toString();
        Files.write(Paths.get(filePath), file.getBytes());
        // 파일이 jpg, png, jpeg, gif 형식이어야 합니다.
        if (!isImageFile(fileName)) throw new FileException("파일이 jpg, png, jpeg, gif 형식이어야 합니다.");
        //파일 객체 생성 저장
        File saveFile = new File(fileName, filePath, file.getSize());
        fileRepository.save(saveFile);
        return saveFile;
    }

    private boolean isImageFile(String fileName) {
        List<String> imageExtensions = List.of(".jpg", ".png", ".jpeg", ".gif");
        String extension = fileName.substring(fileName.lastIndexOf("."));
        return imageExtensions.contains(extension);
    }


    public Resource downloadFile(Long scheduleId) throws FileNotFoundException {
            Optional<Schedule> scheduleOptional = scheduleService.findScheduleById(scheduleId);
            if (scheduleOptional.isEmpty()) {
                throw new ScheduleNotFoundException("일정을 찾을 수 없습니다.");
            }
            // 해당 스케줄에 속한 파일 중 가장 최신 파일을 가져옵니다.
            Optional<File> fileOptional = fileRepository.findByFileId(scheduleOptional.get().getFileId());
            if (fileOptional.isEmpty()) {
                throw new FileNotFoundException("파일을 찾을 수 없습니다.");
            }
            File file = fileOptional.get();
            Path path = Paths.get(file.getFilePath());

            ByteArrayResource resource = null;
            try {
                resource = new ByteArrayResource(Files.readAllBytes(path));
                return resource;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return resource;
    }
}
