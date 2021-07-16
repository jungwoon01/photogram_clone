package com.cos.photogramstart.config;

import com.cos.photogramstart.config.oauth.OAuth2DetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@RequiredArgsConstructor
@EnableWebSecurity // 해당 파일로 시큐리티를 활성화 시킨다.
@Configuration // 시큐리티 설정파일을 IoC 해준다(메모리에 올림).
public class SecurityConfig extends WebSecurityConfigurerAdapter { // 시큐리티 설정을 위해 WebSecurityConfigurerAdapter 를 상속한다.

    private final OAuth2DetailsService oAuth2DetailsService;

    // 비밀번호 해시를 위한 인코더 빈으로 등록
    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 기존의 시큐리티 설정들을 주석처리로 비활성화 시킨다.
        // super.configure(http);

        http.csrf().disable(); // csrf 토큰 검사를 하지 않겠다

        http.authorizeRequests()
                // antMatchers()에 지정된 url 은 인증이 필요하고
                .antMatchers("/", "/user/**", "/image/**", "/subscribe/**", "/comment/**", "/api/**").authenticated()
                // 나머지는 모두 접근 가능하게 설정
                .anyRequest().permitAll()
                // 그리고
                .and()
                // 인증이 필요한 페이지 요청이 오면 formLogin() 을 하는데
                .formLogin()
                // 로그인 페이지는 /auth/signin 이고 (get 방식)
                .loginPage("/auth/signin")
                // post 방식의 요청
                .loginProcessingUrl("/auth/signin")
                // 로그인 성공하면 / 로 이동한다.
                .defaultSuccessUrl("/")
                .and()
                .oauth2Login() // form 로그인도 하는데 oauth2 로그인도 할거야
                .userInfoEndpoint() // oauth2 로그인을 하면 최종응답으로 회원정보를 바로 받을 수 있다.
                .userService(oAuth2DetailsService); // oauth2 로그인에 대한 서비스 등록
    }
}
