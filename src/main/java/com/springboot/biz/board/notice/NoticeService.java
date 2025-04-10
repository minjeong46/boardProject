package com.springboot.biz.board.notice;

import com.springboot.biz.DataNotFoundException;
import com.springboot.biz.user.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NoticeService {
    private final NoticeRepository noticeRepository;

    public Page<Notice> getList(String chk, String kw, int page){
        Pageable pageable = PageRequest.of(page, 10, Sort.by(Sort.Order.desc("createDate")));
        if("subject".equals(chk)){
            return this.noticeRepository.findBySubjectKeyword(kw ,pageable);
        }
        else if("content".equals(chk)){
            return this.noticeRepository.findByContentKeyword(kw ,pageable);
        }

        return this.noticeRepository.findByAllKeyword(kw, pageable);
    }

    public void create(String subject, String content, SiteUser user){
        Notice notice = new Notice();
        notice.setSubject(subject);
        notice.setContent(content.replace("\n", "<br/>"));
        notice.setAuthor(user);
        notice.setCreateDate(LocalDateTime.now());
        this.noticeRepository.save(notice);
    }

    public Notice getNotice(Integer id){
        Optional<Notice> notice = this.noticeRepository.findById(id);
        if(notice.isPresent()){
            return notice.get();
        }
        throw new DataNotFoundException("데이터가 없습니다.");
    }

    public void delete(Integer id){
        this.noticeRepository.deleteById(id);
    }

    public void modify(Notice notice, String subject, String content){
        notice.setSubject(subject);
        notice.setContent(content.replace("\n", "<br/>"));
        notice.setModifyDate(LocalDateTime.now());
        this.noticeRepository.save(notice);
    }


}
