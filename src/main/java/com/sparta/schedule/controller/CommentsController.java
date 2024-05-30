package com.sparta.schedule.controller;

import com.sparta.schedule.dto.CommentsRequestDto;
import com.sparta.schedule.dto.CommentsResponseDto;
import com.sparta.schedule.dto.FileResponseDto;
import com.sparta.schedule.service.CommentsService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
public class CommentsController {
    private final CommentsService commentService;

    public CommentsController(CommentsService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/schedules/{id}/comments")
    @Operation(summary = "파일 등록", description = "사용자에게 파일 등록할 때 사용하는 API")
    public CommentsResponseDto addComment(@PathVariable("id") Long scheduleId, @RequestBody CommentsRequestDto commentsRequestDto) {
        return commentService.addComment(scheduleId, commentsRequestDto);
    }

    @PutMapping("/schedules/{id}/comments/{commentId}")
    @Operation(summary = "파일 등록", description = "사용자에게 파일 등록할 때 사용하는 API")
    public CommentsResponseDto updateComment(@PathVariable("id") Long scheduleId, @PathVariable("commentId") Long commentId, @RequestBody CommentsRequestDto commentsRequestDto) {
        return commentService.updateComment(scheduleId, commentId, commentsRequestDto);
    }
}
