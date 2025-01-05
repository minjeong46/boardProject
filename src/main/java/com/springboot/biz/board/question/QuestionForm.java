package com.springboot.biz.board.question;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestionForm {

    @NotEmpty(message = "제목을 반드시 입력하세요")
    private String subject;

    @NotEmpty(message = "내용을 반드시 입력하세요")
    private String content;
}
