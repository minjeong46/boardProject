package com.springboot.biz.answer;

import com.springboot.biz.board.question.Question;
import com.springboot.biz.board.question.QuestionService;
import com.springboot.biz.user.SiteUser;
import com.springboot.biz.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/answer")
public class AnswerController {
    private final AnswerService answerService;
    private final QuestionService questionService;
    private final UserService userService;

    @PostMapping("/create/{id}")
    public String create(@PathVariable("id") Integer id, @Valid AnswerForm answerForm, BindingResult bindingResult, Model model, Principal principal) {
        Question question = this.questionService.getQuestion(id);
        SiteUser user = this.userService.getUser(principal.getName());
        if (bindingResult.hasErrors()) {
            model.addAttribute("question", question);
            return "question_detail";
        }
        this.answerService.create(question, answerForm.getContent(), user);
        return String.format("redirect:/question/detail/%s", id);
    }
}
