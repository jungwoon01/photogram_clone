package com.cos.photogramstart.handler.aop;

import com.cos.photogramstart.handler.ex.CustomValidationApiException;
import com.cos.photogramstart.handler.ex.CustomValidationException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

@Component // RestController, Service 모든 것들이 Component 를 상속해서 만들어져 있음.
@Aspect // AOP 처리를 하는 클래스라는 것을 스프링에 알려줌.
public class ValidationAdvice {

    // @Around : 어떤 메서드가 실행되기 전 부터 후 까지 관여하고싶을 때 사용.
    // @execution() : 어떤 메서드에 AOP 처리를 해줄 것인가를 설정해주는 것
    // execution() 의 처음 '*' 은 접근 제어자를 지정할 수 있음
    @Around("execution(* com.cos.photogramstart.web.api.*Controller.*(..))") // 웹의 모든 api 컨트롤러에 aop 적용
    public Object apiAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        // proceedingJoinPoint => 메서드의 모든 곳에 접근할 수 있는 변수.
        // 컨트롤러 메서드 보다 먼저 실행

        Object[] args = proceedingJoinPoint.getArgs(); // 메서드의 파라미터들 가져오기

        for (Object arg : args) { // 파라미터를 하나씩 참조하며 반복

            if(arg instanceof BindingResult){ // 유효성 검사 파라미터가 있는지 확인

                BindingResult bindingResult = (BindingResult) arg;

                if(bindingResult.hasErrors()) { // 유효성 오류가 있는지 검사
                    Map<String, String> errorMap = new HashMap<>();

                    for (FieldError error : bindingResult.getFieldErrors()) {
                        errorMap.put(error.getField(), error.getDefaultMessage());
                    }
                    // 유효성 오류 Map을 담아 예외 발생
                    throw new CustomValidationApiException("유효성 검사 실패", errorMap);
                }
            }
        }

        return proceedingJoinPoint.proceed(); // 실제 메서드가 실행됨
    }

    // 웹의 모든 컨트롤러에 aop 적용
    @Around("execution(* com.cos.photogramstart.web.*Controller.*(..))")
    public Object advice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        Object[] args = proceedingJoinPoint.getArgs(); // 메서드의 파라미터들 가져오기

        for (Object arg : args) { // 파라미터를 하나씩 참조하며 반복

            if(arg instanceof BindingResult){ // 유효성 검사 파라미터가 있는지 확인
                BindingResult bindingResult = (BindingResult) arg;

                if(bindingResult.hasErrors()) { // 유효성 검사 오류가 있으면
                    Map<String, String> errorMap = new HashMap<>();

                    for (FieldError error : bindingResult.getFieldErrors()) {
                        errorMap.put(error.getField(), error.getDefaultMessage());
                    }
                    // 유효성 오류 Map을 담아 예외 발생
                    throw new CustomValidationException("유효성 검사 실패", errorMap);
                }

            }
        }

        return proceedingJoinPoint.proceed();
    }
}