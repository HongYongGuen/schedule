package com.sparta.schedule.dto;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class CommentsRequestDto {
    @NotBlank(message = "내용은 필수 입력값입니다.")
    private String content;
    @NotBlank(message = "유저ID는 필수 입력값입니다.")
    private String userId;
}
