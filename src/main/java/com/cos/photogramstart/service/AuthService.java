package com.cos.photogramstart.service;

import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service // 1.IoC 2.트랜잭션 관리
@RequiredArgsConstructor
public class AuthService {

     private final UserRepository userRepository;

     // 회원 가입 서비스
     public User signUp(User user) {
          User userEntity = userRepository.save(user);
          return userEntity;
     }
}
