package com.cos.photogramstart.handler;

import com.cos.photogramstart.handler.ex.CustomValidationException;
import com.cos.photogramstart.util.Script;
import com.cos.photogramstart.web.dto.CMRespDto;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@ControllerAdvice // 모든 예외를 낚아 챈다.
public class ControllerExceptionHandler {

    /*
    CMRespDto, Script 비교
    1. 클라이언트에게 응답할 때는 Script 좋음.
    2. Ajax통신 - CMRespDto 좋음.
    3. Android 통신 - CMRespDto 좋음.
    */

    // CMRespDto 방식
    /*// CustomValidationException 이 발생하면 아래의 메서드가 실행된다.
    @ExceptionHandler(CustomValidationException.class)
    public CMRespDto<?> validationException(CustomValidationException e) { // '?' : 제네릭 타입 리턴 타입을 추론 할 수 있다.
        return new CMRespDto(-1, e.getMessage(), e.getErrorMap()); // 오류 Map 을 리턴
    }*/

    // Script 방식
    // CustomValidationException 이 발생하면 아래의 메서드가 실행된다.
    @ExceptionHandler(CustomValidationException.class)
    public String validationException(CustomValidationException e) { // '?' : 제네릭 타입 리턴 타입을 추론 할 수 있다.
        return Script.back(e.getErrorMap().toString());
    }
}
