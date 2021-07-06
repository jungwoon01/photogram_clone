package com.cos.photogramstart.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CMRespDto<T> {

    private int code; // 1(성공), -1(실패)
    private String message;
    private T data; // 어떤 클래스던지 상관없이 받을 수 있다.
}
