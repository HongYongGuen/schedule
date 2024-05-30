package com.sparta.schedule.service;

import com.sparta.schedule.dto.CommentsRequestDto;
import com.sparta.schedule.dto.CommentsResponseDto;
import com.sparta.schedule.dto.FileResponseDto;
import com.sparta.schedule.entity.Comments;
import com.sparta.schedule.entity.File;
import com.sparta.schedule.entity.Schedule;
import com.sparta.schedule.exception.ScheduleNotFoundException;
import com.sparta.schedule.exception.SchedulesaveException;
import com.sparta.schedule.repository.CommentsRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Service
public class CommentsService {
    ScheduleService scheduleService;
    CommentsRepository commentsRepository;
    public CommentsResponseDto addComment(Long scheduleId, CommentsRequestDto commentsRequestDto) {

        Optional<Schedule> result = scheduleService.findScheduleById(scheduleId);
        if (result.isEmpty()) throw new ScheduleNotFoundException("선택된 일정이 없습니다.");

        if(commentsRequestDto.getContent()==null) throw new IllegalArgumentException("content가 비어있다.");

        Schedule schedule = result.get();

        Comments comments = new Comments(commentsRequestDto);
        schedule.addComments(comments);
        try {
            commentsRepository.save(comments);
        } catch (Exception e) {
            throw new SchedulesaveException("Failed to save schedule");
        }

        return new CommentsResponseDto(comments);

    }
}
