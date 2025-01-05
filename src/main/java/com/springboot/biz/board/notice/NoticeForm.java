package com.springboot.biz.board.notice;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Setter
@Getter
public class NoticeForm {

    @NotEmpty(message = "제목을 입력해주세요.")
    @Length(min = 3, max = 50)
    private String subject;

    @NotEmpty(message = "내용을 입력해주세요.")
    private String content;
}
