package com.cos.photogramstart.config.auth;

import com.cos.photogramstart.domain.user.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

public class PrincipalDetails implements UserDetails {

    private static final long serialVersionUID = 1l;

    private User user;

    public PrincipalDetails(User user) {
        this.user = user;
    }

    // 권한 설정
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Collection<GrantedAuthority> collection = new ArrayList<>();
        collection.add(() -> { return user.getRole(); } );

        return collection;
    }

    public User getUser() {
        return user;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    } // 계정 만료 확인

    @Override
    public boolean isAccountNonLocked() {
        return true;
    } // 계정 잠김 확인

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    } // 크리덴셜 만료 확인

    @Override
    public boolean isEnabled() {
        return true;
    } // 활성, 비활성 확인
}
