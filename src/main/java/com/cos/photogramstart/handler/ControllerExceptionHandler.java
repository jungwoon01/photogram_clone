package com.cos.photogramstart.handler;

import com.cos.photogramstart.handler.ex.CustomApiException;
import com.cos.photogramstart.handler.ex.CustomException;
import com.cos.photogramstart.handler.ex.CustomValidationApiException;
import com.cos.photogramstart.handler.ex.CustomValidationException;
import com.cos.photogramstart.util.Script;
import com.cos.photogramstart.web.dto.CMRespDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ControllerAdvice // 모든 예외를 낚아 챈다.
public class ControllerExceptionHandler {

    /*
    CMRespDto, Script 비교
    1. 클라이언트에게 응답할 때는 Script 좋음.
    2. Ajax통신 - CMRespDto 좋음.
    3. Android 통신 - CMRespDto 좋음.
    */

    // Script 방식
    // CustomValidationException 이 발생하면 아래의 메서드가 실행된다.
    @ExceptionHandler(CustomValidationException.class)
    public ResponseEntity<?> validationException(CustomValidationException e) { // '?' : 제네릭 타입 리턴 타입을 추론 할 수 있다.
        if(e.getErrorMap() == null) {
            return new ResponseEntity<> (Script.back(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<> (Script.back(e.getErrorMap().toString()), HttpStatus.BAD_REQUEST);
    }

    // Script 방식
    // CustomException 이 발생하면 아래의 메서드가 실행된다.
    @ExceptionHandler(CustomException.class)
    public String exception(CustomException e) { // '?' : 제네릭 타입 리턴 타입을 추론 할 수 있다.
        return Script.back(e.getMessage());
    }

    // CMRespDto 방식
    // CustomValidationApiException 이 발생하면 아래의 메서드가 실행된다.
    @ExceptionHandler(CustomValidationApiException.class)
    public ResponseEntity<?> validationApiException(CustomValidationApiException e) { // '?' : 제네릭 타입 리턴 타입을 추론 할 수 있다.
        return new ResponseEntity<>(new CMRespDto<>(-1, e.getMessage(), e.getErrorMap()), HttpStatus.BAD_REQUEST); // 오류 Map 을 리턴
    }

    @ExceptionHandler(CustomApiException.class)
    public ResponseEntity<?> apiException(CustomApiException e) { // '?' : 제네릭 타입 리턴 타입을 추론 할 수 있다.
        return new ResponseEntity<>(new CMRespDto<>(-1, e.getMessage(), null), HttpStatus.BAD_REQUEST);
    }
}
