package com.cos.photogramstart.domain.user;

// JPA - Java Persistence API
// 자바로 데이터를 영구적으로 저장할 수 있는 API를 제공

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity // 디비에 테이블을 생성
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 번호 증가 전략이 데이터베이스를 따라간다.
    private int id;

    private String username;

    private String password;

    private String name;
    private String website;
    private String bio;
    private String email;
    private String phone;
    private String gender;

    private String profileImageUrl;
    private String role;

    private LocalDateTime createDate;

    @PrePersist
    public void createdDate() {
        this.createDate = LocalDateTime.now();
    }
}
