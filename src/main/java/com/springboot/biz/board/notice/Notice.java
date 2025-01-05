package com.springboot.biz.board.notice;

import com.springboot.biz.user.SiteUser;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Notice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 50)
    private String subject;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @CreatedDate
    private LocalDateTime createDate;

    @LastModifiedDate
    private LocalDateTime modifyDate;

    @ManyToOne
    private SiteUser author;

}
