package com.springboot.biz.user;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SiteUserForm {

    @Size(min = 3, max = 20)
    @NotEmpty(message = "사용자 ID를 반드시 입력하세요")
    private String username;

    @NotEmpty(message = "비밀번호를 반드시 입력하세요")
    private String password;

    @NotEmpty(message = "비밀번호 확인을 반드시 입력하세요")
    private String passwordChk;

    @NotEmpty(message = "이메일을 반드시 입력하세요")
    private String email;
}
