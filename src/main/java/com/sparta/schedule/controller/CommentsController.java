package com.sparta.schedule.controller;

import com.sparta.schedule.dto.CommentsRequestDto;
import com.sparta.schedule.dto.CommentsResponseDto;
import com.sparta.schedule.service.CommentsService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Validated
public class CommentsController {
    private final CommentsService commentService;

    public CommentsController(CommentsService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/schedules/{id}/comments")
    @Operation(summary = "댓글 등록", description = "댓글 등록할 때 사용하는 API")
    public CommentsResponseDto addComment(@PathVariable("id") Long scheduleId, @RequestBody CommentsRequestDto commentsRequestDto) {
        return commentService.addComment(scheduleId, commentsRequestDto);
    }

    @PutMapping("/schedules/{id}/comments/{commentId}")
    @Operation(summary = "댓글 수정", description = "댓글 수정할 때 사용하는 API")
    public CommentsResponseDto updateComment(@PathVariable("id") Long scheduleId, @PathVariable("commentId") Long commentId,
                                             @RequestBody CommentsRequestDto commentsRequestDto) {
        return commentService.updateComment(scheduleId, commentId, commentsRequestDto);
    }

    @DeleteMapping("/schedules/{id}/comments/{commentId}/param")
    @Operation(summary = "댓글 삭제", description = "댓글 삭제할 때 사용하는 API")
    public ResponseEntity<String> deleteComment(@PathVariable("id") Long scheduleId, @PathVariable("commentId") Long commentId,
                                                @NotBlank @RequestParam("userId") String userId) {
        commentService.deleteComment(scheduleId, commentId,userId);
        return ResponseEntity.ok().body("댓글을 성공적으로 삭제하였습니다.");
    }
}
