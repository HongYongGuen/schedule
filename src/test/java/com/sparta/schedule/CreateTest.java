package com.sparta.schedule;

import com.sparta.schedule.dto.ScheduleRequestDto;
import com.sparta.schedule.dto.ScheduleResponseDto;
import com.sparta.schedule.entity.Schedule;
import com.sparta.schedule.repository.ScheduleRepository;
import com.sparta.schedule.service.ScheduleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

public class CreateTest {

    @Mock
    private ScheduleRepository scheduleRepository;

    @InjectMocks
    private ScheduleService scheduleService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateSchedule() {
        // given
        ScheduleRequestDto requestDto = new ScheduleRequestDto();
        requestDto.setTitle("Test Schedule");
        requestDto.setDescription("Test Description");
        requestDto.setAssignee("Test Assignee");
        requestDto.setPassword("Test Password");

        Schedule savedSchedule = new Schedule();
        savedSchedule.setId(1L);
        savedSchedule.setTitle("Test Schedule");
        savedSchedule.setDescription("Test Description");
        savedSchedule.setAssignee("Test Assignee");
        savedSchedule.setPassword("Test Password");

        when(scheduleRepository.save(any())).thenReturn(savedSchedule);

        // when
        ScheduleResponseDto responseDto = scheduleService.createSchedule(requestDto);

        // then
        verify(scheduleRepository, times(1)).save(any());
        assert responseDto != null;
        assert responseDto.getId() != null;
        assert responseDto.getTitle().equals("Test Schedule");
        assert responseDto.getDescription().equals("Test Description");
        assert responseDto.getAssignee().equals("Test Assignee");

    }
}