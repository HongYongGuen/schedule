package com.sparta.schedule.service;

import com.sparta.schedule.dto.ScheduleRequestDto;
import com.sparta.schedule.dto.ScheduleResponseDto;
import com.sparta.schedule.entity.File;
import com.sparta.schedule.entity.Schedule;
import com.sparta.schedule.exception.FileException;
import com.sparta.schedule.exception.PasswordMismatchException;
import com.sparta.schedule.exception.ScheduleAlreadyDeletedException;
import com.sparta.schedule.exception.ScheduleNotFoundException;
import com.sparta.schedule.repository.FileRepository;
import com.sparta.schedule.repository.ScheduleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Service
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final FileRepository fileRepository;

    public ScheduleService(ScheduleRepository scheduleRepository,
                           FileRepository fileRepository) {
        this.scheduleRepository = scheduleRepository;
        this.fileRepository = fileRepository;
    }
    public ScheduleResponseDto createSchedule(ScheduleRequestDto scheduleRequestDto) {
        Schedule schedule = new Schedule(scheduleRequestDto);
        Schedule savedSchedule = scheduleRepository.save(schedule);
        return new ScheduleResponseDto(savedSchedule);
    }

    public List<ScheduleResponseDto> getSchedules() {
        return scheduleRepository.findAllByOrderByCreationDateDesc().stream().map(ScheduleResponseDto::new).toList();
    }

    public ScheduleResponseDto getSchedule(Long id) {
        Optional<Schedule> optionalSchedule = findScheduleById(id);
        Schedule schedule = optionalSchedule.orElseThrow(() -> new ScheduleNotFoundException("삭제된 일정입니다."));
        return new ScheduleResponseDto(schedule);
    }
    @Transactional
    public ScheduleResponseDto updateSchedule(Long id, ScheduleRequestDto scheduleRequestDto) {
        Optional<Schedule> optionalSchedule = findScheduleById(id);
        optionalSchedule.ifPresent(schedule -> {
            if (!schedule.checkPassword(scheduleRequestDto.getPassword())) {
                throw new PasswordMismatchException("비밀번호가 일치하지 않습니다.");
            }
            schedule.update(scheduleRequestDto);
        });

        return optionalSchedule.map(ScheduleResponseDto::new).orElseThrow(() -> new ScheduleAlreadyDeletedException("이미 삭제된 일정입니다"));
    }

    public Long deleteSchedule(Long id, String password) {
        Optional<Schedule> optionalSchedule = findScheduleById(id);
        optionalSchedule.ifPresent(schedule -> {
            if (!schedule.checkPassword(password)) {
                throw new PasswordMismatchException("비밀번호가 일치하지 않습니다.");
            }
            scheduleRepository.delete(schedule);
            Long fileId = schedule.getFileId();
            delteFile(fileId);
        });
        return optionalSchedule.map(Schedule::getId).orElseThrow(() -> new ScheduleAlreadyDeletedException("이미 삭제된 일정입니다."));
    }
    private void delteFile(Long fileId) {
        if(fileId != null) {
            Optional<File> fileOptional = fileRepository.findByFileId(fileId);
            if (fileOptional.isPresent()) {
                File file = fileOptional.get();
                Path filePath = Paths.get(file.getFilePath());
                try {
                    Files.deleteIfExists(filePath);
                } catch (IOException e) {
                    throw new FileException("파일 삭제 중 오류가 발생했습니다.");
                }
            }
            fileRepository.deleteById(fileId);
        }else throw new FileException("해당 일정엔 파일이 등록되어 있지 않습니다.");
    }





    public Optional<Schedule> findScheduleById(Long id) {
        Optional<Schedule> result = scheduleRepository.findFirstByOrderByIdDesc();
        if(result.isEmpty()) throw new ScheduleNotFoundException("일정이 하나도 없습니다.");
        if(result.get().getId() < id) throw new ScheduleNotFoundException("입력된 일정 범위를 벗어 났습니다.");
        return scheduleRepository.findById(id);
    }


}