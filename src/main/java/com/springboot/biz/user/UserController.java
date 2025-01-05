package com.springboot.biz.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/signup")
    public String signup(SiteUserForm siteUserForm) {
        return "signup_form";
    }

    @PostMapping("/signup")
    public String signup(@Valid SiteUserForm siteUserForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "signup_form";
        }
        if(!siteUserForm.getPassword().equals(siteUserForm.getPasswordChk())){
            bindingResult.rejectValue("passwordChk", "passwordFaild", "비밀번호가 일치하지 않습니다.");
            return "signup_form";
        }

        try{
            this.userService.create(siteUserForm.getUsername(), siteUserForm.getPassword(), siteUserForm.getEmail());
        }catch (DataIntegrityViolationException e){
            e.printStackTrace();
            bindingResult.reject("singupFaild","이미 등록된 사용자입니다.");
        }catch (Exception e){
            e.printStackTrace();
            bindingResult.reject("singupFaild",e.getMessage());
        }

        return "redirect:/";
    }

    @GetMapping("/login")
    public String login(SiteUserForm siteUserForm) {
        return "login_form";
    }

}
