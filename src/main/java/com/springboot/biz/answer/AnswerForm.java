package com.springboot.biz.answer;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnswerForm {

    @NotEmpty(message = "답변 내용을 반드시 입력해주세요")
    private String content;
}
