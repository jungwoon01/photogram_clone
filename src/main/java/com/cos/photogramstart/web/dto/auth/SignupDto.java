package com.cos.photogramstart.web.dto.auth;

import lombok.Data;

@Data // Getter, Setter 등등
public class SignupDto {
    private String username;
    private String password;
    private String email;
    private String name;
}
