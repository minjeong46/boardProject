package com.springboot.biz.board.notice;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface NoticeRepository extends JpaRepository<Notice, Integer> {

    @Query(
            "select distinct n from Notice n "
                    + "where "
                    + "n.subject like %:kw% "
                    + "or n.content like %:kw%"
    )
    Page<Notice> findByAllKeyword(@Param("kw") String kw, Pageable pageable);


    @Query(
            "select distinct n from Notice n "
                    + "where "
                    + "n.subject like %:kw%"
    )

    Page<Notice> findBySubjectKeyword(@Param("kw") String kw, Pageable pageable);

    @Query(
            "select distinct n from Notice n "
                    + "where "
                    + "n.content like %:kw%"
    )
    Page<Notice> findByContentKeyword(@Param("kw") String kw, Pageable pageable);
 }
