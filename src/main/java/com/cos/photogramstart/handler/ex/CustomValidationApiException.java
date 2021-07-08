package com.cos.photogramstart.handler.ex;

import java.util.Map;

public class CustomValidationApiException extends RuntimeException{

    // 객체를 구분할 때 ( JVM 한테 중요 )
    private static final long serialVersionUID = 1L;

    private Map<String, String> errorMap;

    public CustomValidationApiException(String message, Map<String, String> errorMap) {
        super(message); // 부모의 message 필드에 저장
        this.errorMap = errorMap;
    }

    public Map<String, String> getErrorMap() {
        return errorMap;
    }
}
