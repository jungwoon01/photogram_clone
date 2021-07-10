package com.cos.photogramstart.domain.user;

// JPA - Java Persistence API
// 자바로 데이터를 영구적으로 저장할 수 있는 API를 제공

import com.cos.photogramstart.domain.image.Image;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity // 디비에 테이블을 생성
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 번호 증가 전략이 데이터베이스를 따라간다.
    private int id;

    @Column(unique = true) // username 중복을 막기 위해 unique 제약조건 추가
    private String username;
    @Column(nullable = false) // not null 로 지정
    private String password;
    @Column(nullable = false)
    private String name;
    private String website;
    private String bio;
    @Column(nullable = false)
    private String email;
    private String phone;
    private String gender;

    private String profileImageUrl;
    private String role;

    // 나는 연관관계의 주인이 아니다. 그러므로 테이블에 컬럼을 만들지마.
    // User 를 Select 할 대 해당 User id로 등록된 image 들을 다 가져와
    // LAZY = USER 를 Select 할 때 해당 User id 로 등록된 image 들을 가져오지마 - 대신 getImages() 함수가 호출 될 때 가져와.
    // Eager = User 를 Select 할 때 해당 User id로 등록된 image 들을 전부 Join 해서 가져와!
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Image> images; // 양방향 매핑

    private LocalDateTime createDate;

    @PrePersist
    public void createdDate() {
        this.createDate = LocalDateTime.now();
    }
}
