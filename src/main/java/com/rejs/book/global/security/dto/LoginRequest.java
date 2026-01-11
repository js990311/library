package com.rejs.book.global.security.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Setter
public class LoginRequest {
    @NotBlank(message = "id는 필수입니다.")
    @Size(min = 4, max = 20, message = "id는 4~20자 이내로 지어주십시오")
    private String username;

    @NotBlank
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d).{8,}$",
            message = "비밀번호는 영문, 숫자 포함 8자 이상이어야 합니다.")
    private String password;
}
