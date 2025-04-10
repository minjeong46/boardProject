package com.springboot.biz.answer;

import com.springboot.biz.board.question.Question;
import com.springboot.biz.board.question.QuestionService;
import com.springboot.biz.user.SiteUser;
import com.springboot.biz.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/answer")
public class AnswerController {
    private final AnswerService answerService;
    private final QuestionService questionService;
    private final UserService userService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create/{id}")
    public String create(@Valid AnswerForm answerForm, BindingResult bindingResult, @PathVariable("id") Integer id, Model model, Principal principal) {
        Question question = this.questionService.getQuestion(id);
        SiteUser user = this.userService.getUser(principal.getName());
        if (bindingResult.hasErrors()) {
            model.addAttribute("question", question);
            return "question_detail";
        }
        Answer answer = this.answerService.create(answerForm.getContent(), question, user);
        return String.format("redirect:/question/detail/%s#answer_%s", answer.getQuestion().getId(), answer.getId());
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    public String modify(@PathVariable("id") Integer id, Model model, Principal principal, AnswerForm answerForm) {

        Answer answer = this.answerService.get(id);
        if(!answer.getAuthor().getUsername().equals(principal.getName())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"수정 권한이 없습니다.");
        }
        model.addAttribute("answer", answer);
        answerForm.setContent(answer.getContent());
        return "answer_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{id}")
    public String modify(@PathVariable("id") Integer id, @Valid AnswerForm answerForm, BindingResult bindingResult, Model model, Principal principal) {
        Answer answer = this.answerService.get(id);
        if (bindingResult.hasErrors()) {
            model.addAttribute("answer", answer);
            return "answer_form";
        }

        this.answerService.modify(answer, answerForm.getContent());
        return String.format("redirect:/question/detail/%s#answer_%s",answer.getQuestion().getId(),answer.getId());
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id, Principal principal) {
        Answer answer = this.answerService.get(id);
        if(!answer.getAuthor().getUsername().equals(principal.getName())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"삭제 권한이 없습니다.");
        }

        this.answerService.delete(id);
        return String.format("redirect:/question/detail/%s",answer.getQuestion().getId());
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/voter/{id}")
    public String voter(@PathVariable("id") Integer id, Model model, Principal principal) {
        Answer answer = this.answerService.get(id);
        SiteUser user = this.userService.getUser(principal.getName());
        this.answerService.voter(answer, user);
        return String.format("redirect:/question/detail/%s#answer_%s",answer.getQuestion().getId(),answer.getId());
    }
}
