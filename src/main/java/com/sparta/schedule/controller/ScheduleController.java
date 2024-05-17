package com.sparta.schedule.controller;

import com.sparta.schedule.dto.ScheduleRequestDto;
import com.sparta.schedule.dto.ScheduleResponseDto;
import com.sparta.schedule.service.ScheduleService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@Validated
@Tag(name = "Response Estimate", description = "Response Estimate API")
public class
ScheduleController {
    private final ScheduleService scheduleService;
    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }
    @PostMapping("/schedules")
    @Operation(summary = "일정 등록", description = "사용자가 일정 등록할 때 사용하는 API")
    public ScheduleResponseDto createSchedule(@Valid @RequestBody ScheduleRequestDto scheduleRequestDto) {
        return scheduleService.createSchedule(scheduleRequestDto);
    }

    @GetMapping("schedules")
    @Operation(summary = "일정 전체 조회", description = "사용자가 일정 전체 조회할 때 사용하는 API")
    public List<ScheduleResponseDto> getSchedules() {
        return scheduleService.getSchedules();
    }
    @GetMapping("/schedules/{id}")
    @Operation(summary = "특정 일정 조회", description = "사용자가 특정 일정 조회할 때 사용하는 API")
    public ScheduleResponseDto getSchedule(@PathVariable Long id) {
        return scheduleService.getSchedule(id);
    }
    @PutMapping("/schedules/{id}")
    @Operation(summary = "특정 일정 수정", description = "사용자가 특정 일정 수정할 때 사용하는 API")
    public ScheduleResponseDto updateSchedule(@PathVariable Long id, @Valid @RequestBody ScheduleRequestDto scheduleRequestDto) {
        return scheduleService.updateSchedule(id, scheduleRequestDto);
    }
    @DeleteMapping("/schedules/{id}/param")
    @Operation(summary = "특정 일정 삭제", description = "사용자가 특정 일정 삭제할 때 사용하는 API")
    public Long deleteSchedule(@PathVariable Long id, @NotBlank @RequestParam String password) {
        return scheduleService.deleteSchedule(id,password);
    }

}
