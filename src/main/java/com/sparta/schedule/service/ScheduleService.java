package com.sparta.schedule.service;

import com.sparta.schedule.dto.ScheduleRequestDto;
import com.sparta.schedule.dto.ScheduleResponseDto;
import com.sparta.schedule.entity.Schedule;
import com.sparta.schedule.repository.ScheduleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;

    public ScheduleService(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    public ScheduleResponseDto createSchedule(ScheduleRequestDto scheduleRequestDto) {
        Schedule schedule = new Schedule(scheduleRequestDto);
        Schedule savedSchedule = scheduleRepository.save(schedule);
        return new ScheduleResponseDto(savedSchedule);
    }

    public List<ScheduleResponseDto> getSchedules() {
        return scheduleRepository.findAll().stream().map(ScheduleResponseDto::new).toList();
    }

    public ScheduleResponseDto getSchedule(Long id) {
        Schedule schedule = findScheduleById(id);
        return new ScheduleResponseDto(schedule);
    }

    public Long updateSchedule(Long id, ScheduleRequestDto scheduleRequestDto) {
        return null;
    }

    public Long deleteSchedule(Long id) {
        return null;
    }

    private Schedule findScheduleById(Long id) {
        return scheduleRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("찾는 일정이 존재하지 않습니다."));
    }
}
