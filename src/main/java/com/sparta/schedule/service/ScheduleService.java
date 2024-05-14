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
        return null;
    }

    public ScheduleResponseDto getSchedule(Long id) {
        Optional<Schedule> result = scheduleRepository.findById(id);
        if(result.isPresent()) {
            Schedule schedule = result.get();
            return new ScheduleResponseDto(schedule);
        }
        else return null;
    }

    public Long updateSchedule(Long id, ScheduleRequestDto scheduleRequestDto) {
        return null;
    }

    public Long deleteSchedule(Long id) {
        return null;
    }
}
