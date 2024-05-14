package com.sparta.schedule.dto;

import lombok.Getter;

@Getter
public class ScheduleRequestDto {
    private String title;
    private String description;
    private String assignee;
    private String password;

}
