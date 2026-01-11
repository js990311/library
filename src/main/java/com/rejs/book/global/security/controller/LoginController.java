package com.rejs.book.global.security.controller;

import com.rejs.book.global.security.dto.LoginRequest;
import com.rejs.book.global.security.dto.SignupRequest;
import com.rejs.book.global.security.exception.UsernameAlreadyExistsException;
import com.rejs.book.global.security.service.SignupService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RequiredArgsConstructor
@Controller
public class LoginController {
    private final SignupService signupService;

    @GetMapping("/")
    public String indexPage(){
        return "index";
    }


    @GetMapping("/login")
    public String loginPage(Model model){
        model.addAttribute("loginForm", new LoginRequest());
        return "user/login";
    }

    @GetMapping("/signup")
    public String signupPage(Model model){
        model.addAttribute("signupForm", new SignupRequest());
        return "user/signup";
    }

    @PostMapping("/signup")
    public String signup(@Valid @ModelAttribute("signupForm") SignupRequest request, BindingResult bindingResult, RedirectAttributes redirectAttributes){
        if(bindingResult.hasErrors()){
            return "user/signup";
        }
        try {
            signupService.signup(request);
        }catch (UsernameAlreadyExistsException e){
            bindingResult.rejectValue("username", "already.exists", "이미 사용 중인 아이디입니다.");
            return "user/signup";
        }
        redirectAttributes.addFlashAttribute("redirectMsg", "회원가입 성공했습니다. 로그인해주십시오");
        return "redirect:/login";
    }

}
