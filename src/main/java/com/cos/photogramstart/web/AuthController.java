package com.cos.photogramstart.web;

import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.handler.ex.CustomValidationException;
import com.cos.photogramstart.service.AuthService;
import com.cos.photogramstart.web.dto.auth.SignupDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Controller // 1. IoC 2. 파일을 리턴하는 컨트롤러
@RequiredArgsConstructor
public class AuthController {

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
    // @Valid : SignupDto 에 설정해준 유효성 검사를 진행한다.
    // BindingResult : @Valid 의 유효성 검사에서 오류가 있는지 확인 할 수 있다.
    @PostMapping("/auth/signup")
    public String signup(@Valid SignupDto signupDto, BindingResult bindingResult) {

        if(bindingResult.hasErrors()) { // 유효성 검사 오류가 있으면
            Map<String, String> errorMap = new HashMap<>();

            for (FieldError error : bindingResult.getFieldErrors()) {
                errorMap.put(error.getField(), error.getDefaultMessage());
            }
            throw new CustomValidationException("유효성 검사 실패", errorMap);
        }else{ // 유효성 검사 오류가 없으면
            User user = signupDto.toEntity();
            User userEntity = authService.signUp(user);

            return "/auth/signin";
        }
    }
}
