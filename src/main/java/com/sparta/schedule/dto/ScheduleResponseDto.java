package com.sparta.schedule.dto;

import com.sparta.schedule.entity.Schedule;
import jakarta.persistence.Column;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ScheduleResponseDto {
    private Long id;
    private String title;
    private String description;
    private String assignee;
    private String password;
    private LocalDateTime creationDate;
    //private LocalDateTime modifiedAt;

    public ScheduleResponseDto(Schedule schedule) {
        this.id = schedule.getId();
        this.title = schedule.getTitle();
        this.description = schedule.getDescription();
        this.assignee = schedule.getAssignee();
        this.password = schedule.getPassword();
        this.creationDate = schedule.getCreationDate();
    }
}
