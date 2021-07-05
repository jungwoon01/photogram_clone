package com.cos.photogramstart.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity // 해당 파일로 시큐리티를 활성화 시킨다.
@Configuration // 시큐리티 설정파일을 IoC 해준다(메모리에 올림).
public class SecurityConfig extends WebSecurityConfigurerAdapter { // 시큐리티 설정을 위해 WebSecurityConfigurerAdapter 를 상속한다.

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 기존의 시큐리티 설정들을 주석처리로 비활성화 시킨다.
        // super.configure(http);

        http.authorizeRequests()
                // antMatchers()에 지정된 url 은 인증이 필요하고
                .antMatchers("/", "/user/**", "/image/**", "/subscribe/**", "/comment/**").authenticated()
                // 나머지는 모두 접근 가능하게 설정
                .anyRequest().permitAll()
                // 그리고
                .and()
                // 인증이 필요한 페이지 요청이 오면 formLogin() 을 하는데
                .formLogin()
                // 로그인 페이지는 /auth/signin 이고
                .loginPage("/auth/signin")
                // 로그인 성공하면 / 로 이동한다.
                .defaultSuccessUrl("/");
    }
}
