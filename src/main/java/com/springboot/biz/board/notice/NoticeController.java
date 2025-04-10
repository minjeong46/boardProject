package com.springboot.biz.board.notice;

import com.springboot.biz.board.question.Question;
import com.springboot.biz.user.SiteUser;
import com.springboot.biz.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/notice")
public class NoticeController {
    private final NoticeService noticeService;

    private final UserService userService;

    @GetMapping("/list")
    public String listNotice(@RequestParam(value = "page", defaultValue = "0") Integer page, @RequestParam(value = "kw", defaultValue = "") String kw,
                             @RequestParam(value = "chk", defaultValue = "subject") String chk, Model model) {
        Page<Notice> paging = this.noticeService.getList(chk, kw, page);
        model.addAttribute("paging", paging);
        model.addAttribute("kw", kw);
        model.addAttribute("chk", chk);
        return "notice_list";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/create")
    public String createNotice( Principal principal, Model model, NoticeForm noticeForm) {
        SiteUser user = userService.getUser(principal.getName());
        model.addAttribute("user", user);
        return "notice_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create")
    public String createNotice(Notice notice, Model model, @Valid NoticeForm noticeForm, BindingResult bindingResult, Principal principal) {
        if (bindingResult.hasErrors()) {
            return "notice_form";
        }
        SiteUser user = userService.getUser(principal.getName());
        this.noticeService.create(noticeForm.getSubject(), noticeForm.getContent(), user);
        return "redirect:/notice/list";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String deleteNotice(@PathVariable("id") Integer id, Principal principal) {
        Notice notice = this.noticeService.getNotice(id);
        if(!notice.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"삭제 권한이 없습니다.");
        }
        this.noticeService.delete(id);
        return "redirect:/notice/list";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    public String modifyNotice(@PathVariable("id") Integer id, NoticeForm noticeForm, Principal principal, Model model) {
        Notice notice = this.noticeService.getNotice(id);
        SiteUser user = userService.getUser(principal.getName());
        model.addAttribute("user", user);

        noticeForm.setSubject(notice.getSubject());
        noticeForm.setContent(notice.getContent());

        return "notice_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{id}")
    public String modifyNotice(@PathVariable("id") Integer id, @Valid NoticeForm noticeForm, BindingResult bindingResult, Principal principal) {
        Notice notice = this.noticeService.getNotice(id);
        if(!notice.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다.");
        }

        if(bindingResult.hasErrors()) {
            return "notice_form";
        }
        this.noticeService.modify(notice, noticeForm.getSubject(), noticeForm.getContent());
        return "redirect:/notice/list";
    }

}
