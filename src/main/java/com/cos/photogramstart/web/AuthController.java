package com.cos.photogramstart.web;

import com.cos.photogramstart.web.dto.auth.SignupDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller // 1. IoC 2. 파일을 리턴하는 컨트롤러
public class AuthController {

    Logger log = LoggerFactory.getLogger(AuthController.class);

    // 로그인 페이지
    @GetMapping("/auth/signin")
    public String signinForm() {
        return "auth/signin";
    }

    // 가입 페이지
    @GetMapping("/auth/signup")
    public String signupForm() {
        return "auth/signup";
    }

    // 회원가입버튼 -> /auth/signup -> /auth/signin
    @PostMapping("/auth/signup") // key = value (x-www-form-urlencoded)
    public String signup(SignupDto signupDto) {
        log.info(signupDto.toString());
        return "auth/signin";
    }
}
