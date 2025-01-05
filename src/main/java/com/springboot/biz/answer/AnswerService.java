package com.springboot.biz.answer;

import com.springboot.biz.board.question.Question;
import com.springboot.biz.user.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AnswerService {
    private final AnswerRepository answerRepository;

    public void create(Question question, String content, SiteUser siteUser) {
        Answer answer = new Answer();
        answer.setQuestion(question);
        answer.setContent(content);
        answer.setCreateDate(LocalDateTime.now());
        answer.setAuthor(siteUser);
        this.answerRepository.save(answer);
    }
}
