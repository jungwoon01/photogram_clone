package com.cos.photogramstart.web;

import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.service.AuthService;
import com.cos.photogramstart.web.dto.auth.SignupDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller // 1. IoC 2. 파일을 리턴하는 컨트롤러
@RequiredArgsConstructor
public class AuthController {

    Logger log = LoggerFactory.getLogger(AuthController.class);

    // @RequiredArgsConstructor 를 통해 생성자가 자동으로 만들어 졌기 때문에
    // IoC에 등록된 Bean 중에서 AuthService 를 찾아서 주입해준다.
    private final AuthService authService;

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

        User user = signupDto.toEntity();
        User userEntity = authService.signUp(user);

        System.out.println(userEntity);
        return "auth/signin";
    }
}
