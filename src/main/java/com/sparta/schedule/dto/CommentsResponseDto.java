package com.sparta.schedule.dto;

import com.sparta.schedule.entity.Comments;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentsResponseDto {
    private Long id;
    private String content;
    private String userId;
    private Long scheduleId;
    private LocalDateTime creationDate;

    public CommentsResponseDto(Comments comments) {
        this.id = comments.getId();
        this.content = comments.getContent();
        this.userId = comments.getUserId();
        this.scheduleId = comments.getSchedule().getId();
        this.creationDate = comments.getCreationDate();
    }
}
