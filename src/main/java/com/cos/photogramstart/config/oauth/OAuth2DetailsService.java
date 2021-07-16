package com.cos.photogramstart.config.oauth;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class OAuth2DetailsService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);
        // 유저에 대한 정보가 담겨있는 것(Attributes)을 userInfo Map 에 저장한다.
        Map<String, Object> userInfo = oAuth2User.getAttributes();

        String username = "facebook_" + (String) userInfo.get("id");
        String name = (String) userInfo.get("name");
        String email = (String) userInfo.get("email");

        User userEntity = userRepository.findByUsername(username);

        if(userEntity == null) { // 페이스북 최초 로그인
            User user = User.builder()
                    .username(username)
                    .password(new BCryptPasswordEncoder().encode(UUID.randomUUID().toString()))
                    .email(email)
                    .name(name)
                    .role("ROLE_USER")
                    .build();

            return new PrincipalDetails(userRepository.save(user), userInfo);
        }else { // 이미 페이스북으로 회원가입이 되어 있다는 뜻
            return new PrincipalDetails(userEntity, userInfo);
        }
    }
}
