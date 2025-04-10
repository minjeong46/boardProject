package com.springboot.biz;

import com.springboot.biz.answer.AnswerRepository;
import com.springboot.biz.board.notice.Notice;
import com.springboot.biz.board.notice.NoticeRepository;
import com.springboot.biz.board.question.Question;
import com.springboot.biz.board.question.QuestionRepository;
import com.springboot.biz.user.SiteUser;
import com.springboot.biz.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Optional;

@SpringBootTest
class BoardProjectApplicationTests {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private NoticeRepository noticeRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void contextLoads() {

//        Question q = new Question();
//        q.setSubject("테스트 게시글");
//        q.setContent("내용");
//        q.setCreateDate(LocalDateTime.now());
//        this.questionRepository.save(q);

//        Optional<Question> q2 = this.questionRepository.findById(1);
//        Question q3 = q2.get();
//        if(q2.isPresent()) {
//            Answer a = new Answer();
//            a.setContent("테스트 답글");
//            a.setCreateDate(LocalDateTime.now());
//            a.setQuestion(q3);
//            this.answerRepository.save(a);
//        }
//
//        for(int i=0; i<100; i++) {
//            Question q = new Question();
//            q.setSubject(String.format("제목 테스트 [%03d]",i));
//            q.setContent("내용");
//            q.setCreateDate(LocalDateTime.now());
//            this.questionRepository.save(q);
//        }

//        Notice notice = new Notice();
//        notice.setSubject("공지사항 게시판입니다.222");
//        notice.setContent("게시판 내용을 작성합니다.22222");
//        notice.setCreateDate(LocalDateTime.now());
//        this.noticeRepository.save(notice);

        Optional<SiteUser> user = this.userRepository.findByUsername("user1");
        System.out.println(user.get().getUsername());


    }

}
