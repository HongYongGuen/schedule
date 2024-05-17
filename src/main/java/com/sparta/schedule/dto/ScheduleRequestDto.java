package com.sparta.schedule.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScheduleRequestDto {
    @NotBlank(message = "할일 제목은 필수 입력값입니다.")
    @Size(max = 200, message = "할일 제목은 200자 이내로 입력하세요.")
    private String title;
    private String description;
    @NotBlank(message = "담당자는 필수 입력값입니다.")
    @Email(message = "담당자는 이메일 형식이어야 합니다.")
    private String assignee;
    @NotBlank(message = "비밀번호는 필수 입력값입니다.")
    private String password;
}
