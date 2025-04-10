package com.springboot.biz.answer;

import com.springboot.biz.DataNotFoundException;
import com.springboot.biz.board.question.Question;
import com.springboot.biz.user.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AnswerService {
    private final AnswerRepository answerRepository;

    public Answer create(String content, Question question, SiteUser user) {
        Answer answer = new Answer();
        answer.setContent(content);
        answer.setQuestion(question);
        answer.setCreateDate(LocalDateTime.now());
        answer.setAuthor(user);
        this.answerRepository.save(answer);
        return answer;
    }

    public Answer get(Integer id) {
        Optional<Answer> answer = this.answerRepository.findById(id);
        if (answer.isPresent()) {
            return answer.get();
        }
        throw new DataNotFoundException("답변을 찾을 수 없습니다.");
    }

    public void modify(Answer answer, String content) {
        answer.setContent(content);
        answer.setModifyDate(LocalDateTime.now());
        this.answerRepository.save(answer);
    }

    public void delete(Integer id) {
        this.answerRepository.deleteById(id);
    }

    public void voter(Answer answer, SiteUser user) {
        answer.getVoter().add(user);
        this.answerRepository.save(answer);
    }
}
