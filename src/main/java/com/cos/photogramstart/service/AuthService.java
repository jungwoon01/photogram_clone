package com.cos.photogramstart.service;

import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service // 1.IoC 2.트랜잭션 관리
@RequiredArgsConstructor
public class AuthService {

     private final UserRepository userRepository;
     // 비밀번호 해시를 위한 필드
     private final BCryptPasswordEncoder bCryptPasswordEncoder;

     // 회원 가입 서비스
     @Transactional // Write(Insert, Update, Delete)
     public User signUp(User user) {

          // 비밀번호 해시코드로 인코딩
          String rawPassword = user.getPassword();
          String encPassword = bCryptPasswordEncoder.encode(rawPassword);
          user.setPassword(encPassword);
          // 룰 추가
          user.setRole("ROLE_USER");

          User userEntity = userRepository.save(user);

          return userEntity;
     }
}
