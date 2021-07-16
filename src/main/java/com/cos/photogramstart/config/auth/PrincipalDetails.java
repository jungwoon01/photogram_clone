package com.cos.photogramstart.config.auth;

import com.cos.photogramstart.domain.user.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class PrincipalDetails implements UserDetails, OAuth2User {

    private static final long serialVersionUID = 1l;

    private User user;
    private Map<String, Object> attributes;

    public PrincipalDetails(User user) {
        this.user = user;
    }

    public PrincipalDetails(User user, Map<String, Object> attributes) {
        this.user = user;
        this.attributes = attributes;
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

    public void setUser(User user) {
        this.user = user;
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

    @Override
    public Map<String, Object> getAttributes() {
        return null;
    }

    @Override
    public String getName() {
        return (String) attributes.get("name");
    }
}
