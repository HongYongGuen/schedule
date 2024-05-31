package com.sparta.schedule.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class SignupRequestDto {
    @NotBlank(message = "별명은 필수 입력값입니다.")
    private String nickname;
    @NotBlank(message = "사용자이름은 필수 입력값입니다.")
    @Size(min = 4, max = 10, message = "사용자이름은 4자 이상, 10자 이하로 입력하세요.")
    @Pattern(regexp = "^[a-z0-9]+$", message = "사용자이름은 알파벳 소문자와 숫자로만 구성되어야 합니다.")
    private String username;
    @NotBlank(message = "비밀번호는 필수 입력값입니다.")
    @Size(min = 8, max = 15, message = "비밀번호는 8자 이상, 15자 이하로 입력하세요.")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]+$", message = "비밀번호는 알파벳 대소문자와 숫자로 구성되어야 합니다.")
    private String password;
    private boolean admin = false;
    private String adminToken = "";
}
