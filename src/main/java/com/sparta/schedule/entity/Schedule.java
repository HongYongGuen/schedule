package com.sparta.schedule.entity;

import com.sparta.schedule.dto.ScheduleRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
@Entity
@Getter
@Setter
@Table(name = "schedule")
@NoArgsConstructor
public class Schedule extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "description", nullable = false, length = 500)
    private String description;
    @Column(name = "assignee", nullable = false)
    private String assignee;
    @Column(name = "password", nullable = false)
    private String password;

    public Schedule(ScheduleRequestDto scheduleRequestDto) {
        this.title = scheduleRequestDto.getTitle();
        this.description = scheduleRequestDto.getDescription();
        this.assignee = scheduleRequestDto.getAssignee();
        this.password = scheduleRequestDto.getPassword();
    }
}
