package com.springboot.biz.board.question;

import com.springboot.biz.answer.AnswerForm;
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
@RequestMapping("/question")
public class QuestionController {
    private final QuestionService questionService;
    private final UserService userService;

    @GetMapping("/list")
    public String list(@RequestParam(value = "page", defaultValue = "0") int page ,
            @RequestParam(value = "kw" , defaultValue = "") String kw, Model model) {
        Page<Question> paging = this.questionService.getList(page, kw);
        model.addAttribute("paging", paging);
        model.addAttribute("kw", kw);
        return "question_list";
    }

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable("id") Integer id, Model model, AnswerForm answerForm, Principal principal) {
        Question question = this.questionService.getQuestion(id);
        this.questionService.cntQuestion(id);

        model.addAttribute("question", question);

        if(principal != null) {
            SiteUser user = this.userService.getUser(principal.getName());
            model.addAttribute("user", user);
        }

        return "question_detail";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/create")
    public String create(QuestionForm questionForm) {
        return "question_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create")
    public String create(@Valid QuestionForm questionForm, BindingResult bindingResult, Principal principal, Model model) {

        SiteUser user = this.userService.getUser(principal.getName());

        if (bindingResult.hasErrors()) {
            return "question_form";
        }
        this.questionService.addQuestion(questionForm.getSubject(), questionForm.getContent(), user);
        return "redirect:/question/list";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    public String modify(@PathVariable("id") Integer id,  QuestionForm questionForm, Principal principal) {
        Question question = this.questionService.getQuestion(id);
        if(!question.getAuthor().getUsername().equals(principal.getName())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다.");
        }
        questionForm.setSubject(question.getSubject());
        questionForm.setContent(question.getContent());
        return "question_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{id}")
    public String modify(@PathVariable("id") Integer id, @Valid QuestionForm questionForm, BindingResult bindingResult, Principal principal) {

        if(bindingResult.hasErrors()) {
            return "question_form";
        }

        Question question = this.questionService.getQuestion(id);
        if(!question.getAuthor().getUsername().equals(principal.getName())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다.");
        }
        this.questionService.modifyQuestion(questionForm.getSubject(), questionForm.getContent(),question);
        return String.format("redirect:/question/detail/%s", id);
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id) {
        this.questionService.deleteQuestion(id);
        return "redirect:/question/list";
    }

    @GetMapping("/voter")
    public String voter(@RequestParam("id") Integer id, Principal principal) {
        Question question = this.questionService.getQuestion(id);
        SiteUser user = this.userService.getUser(principal.getName());
        this.questionService.voter(question,user);
        return String.format("redirect:/question/detail/%s", id);
    }

}
