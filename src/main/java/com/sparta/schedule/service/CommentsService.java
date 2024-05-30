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
import com.sparta.schedule.repository.ScheduleRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Service
public class CommentsService {
    ScheduleService scheduleService;
    ScheduleRepository scheduleRepository;
    CommentsRepository commentsRepository;
    public CommentsResponseDto addComment(Long scheduleId, CommentsRequestDto commentsRequestDto) {

        Optional<Schedule> result = scheduleService.findScheduleById(scheduleId);
        if (result.isEmpty()) throw new ScheduleNotFoundException("선택된 일정이 없습니다.");

        if(commentsRequestDto.getContent()==null) throw new IllegalArgumentException("content가 비어있다.");

        Schedule schedule = result.get();

        Comments comments = new Comments(commentsRequestDto);
        schedule.addComments(comments);
        scheduleRepository.save(schedule);

        try {
            commentsRepository.save(comments);
        } catch (Exception e) {
            throw new SchedulesaveException("Failed to save schedule");
        }

        return new CommentsResponseDto(comments);

    }


    public CommentsResponseDto updateComment(Long scheduleId, Long commentId, CommentsRequestDto commentsRequestDto) {

        Optional<Schedule> result = scheduleService.findScheduleById(scheduleId);
        if (result.isEmpty()) throw new ScheduleNotFoundException("선택된 일정이 없습니다.");

        if(commentsRequestDto.getContent()==null || commentId == null) throw new IllegalArgumentException("content와 commentId가 비어있다.");

        Comments comments = commentsRepository.findById(commentId).orElseThrow(() -> new IllegalArgumentException("commentId가 존재하지 않습니다."));

        if(commentsRequestDto.getUserId()!=comments.getUserId()) throw new IllegalArgumentException("userId가 일치하지 않는다.");

        comments.update(commentsRequestDto);

        try{
            commentsRepository.save(comments);
        }
        catch(Exception e){
            throw new SchedulesaveException("Failed to save schedule");
        }

        return new CommentsResponseDto(comments);
    }
}
