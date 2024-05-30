package com.sparta.schedule.service;

import com.sparta.schedule.dto.CommentsRequestDto;
import com.sparta.schedule.dto.CommentsResponseDto;
import com.sparta.schedule.entity.Comments;
import com.sparta.schedule.entity.Schedule;
import com.sparta.schedule.exception.ScheduleNotFoundException;
import com.sparta.schedule.exception.SchedulesaveException;
import com.sparta.schedule.exception.UnauthorizedException;
import com.sparta.schedule.repository.CommentsRepository;
import com.sparta.schedule.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CommentsService {
    ScheduleService scheduleService;
    ScheduleRepository scheduleRepository;
    CommentsRepository commentsRepository;

    @Autowired
    public CommentsService(ScheduleService scheduleService, ScheduleRepository scheduleRepository, CommentsRepository commentsRepository) {
        this.scheduleService = scheduleService;
        this.scheduleRepository = scheduleRepository;
        this.commentsRepository = commentsRepository;
    }

    @Transactional
    public CommentsResponseDto addComment(Long scheduleId, CommentsRequestDto commentsRequestDto) {

        Optional<Schedule> result = scheduleService.findScheduleById(scheduleId);
        if (result.isEmpty()) throw new ScheduleNotFoundException("선택된 일정이 없습니다.");

        Schedule schedule = result.get();

        Comments comments = new Comments(commentsRequestDto);

        schedule.addComments(comments);



        try {
            scheduleRepository.save(schedule);
            commentsRepository.save(comments);

        } catch (Exception e) {
            throw new SchedulesaveException("Failed to save schedule");
        }


        return new CommentsResponseDto(comments);

    }


    public CommentsResponseDto updateComment(Long scheduleId, Long commentId, CommentsRequestDto commentsRequestDto) {

        Optional<Schedule> result = scheduleService.findScheduleById(scheduleId);
        if (result.isEmpty()) throw new ScheduleNotFoundException("선택된 일정이 없습니다.");

        if(scheduleId==null || commentId == null) throw new IllegalArgumentException("scheduleId, commentId가 비어있다.");

        Comments comments = commentsRepository.findById(commentId).orElseThrow(() -> new IllegalArgumentException("commentId가 존재하지 않습니다."));

        if(!commentsRequestDto.getUserId().equals(comments.getUserId())) throw new UnauthorizedException("사용자가 댓글을 삭제할 권한이 없습니다.");

        comments.update(commentsRequestDto);

        try{
            commentsRepository.save(comments);
        }
        catch(Exception e){
            throw new SchedulesaveException("Failed to save schedule");
        }

        return new CommentsResponseDto(comments);
    }

    public Long deleteComment(Long scheduleId, Long commentId, String userId) {

        Optional<Schedule> result = scheduleService.findScheduleById(scheduleId);
        if (result.isEmpty()) throw new ScheduleNotFoundException("선택된 일정이 없습니다.");

        if(scheduleId==null || commentId == null) throw new IllegalArgumentException("scheduleId, commentId가 비어있다.");

        Comments comments = commentsRepository.findById(commentId).orElseThrow(() -> new IllegalArgumentException("commentId가 존재하지 않습니다."));

        if(!userId.equals(comments.getUserId())) throw new UnauthorizedException("사용자가 댓글을 삭제할 권한이 없습니다.");

//        Schedule schedule = result.get();
//        schedule.removeComments(comments);
//        scheduleRepository.save(schedule);
        commentsRepository.delete(comments);
        return commentId;
    }
}
