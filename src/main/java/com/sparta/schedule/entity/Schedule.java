package com.sparta.schedule.entity;

import com.sparta.schedule.dto.ScheduleRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

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
    @Column(name = "assignee")
    private String assignee;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "FileId")
    private Long fileId;

    @OneToMany(mappedBy = "schedule", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comments> comments;


    public Schedule(ScheduleRequestDto scheduleRequestDto) {
        this.title = scheduleRequestDto.getTitle();
        this.description = scheduleRequestDto.getDescription();
        this.assignee = scheduleRequestDto.getAssignee();
        this.password = scheduleRequestDto.getPassword();
    }

    public void update(ScheduleRequestDto scheduleRequestDto) {
        this.title = scheduleRequestDto.getTitle();
        this.description = scheduleRequestDto.getDescription();
        this.assignee = scheduleRequestDto.getAssignee();

    }
    public void updateFileId(Long fileId) {
        this.fileId = fileId;
    }

    public boolean checkPassword(String password) {
        return this.password.equals(password);

    }

    public void addComments(Comments comments) {
        this.comments.add(comments);
        comments.setSchedule(this);
    }
}
