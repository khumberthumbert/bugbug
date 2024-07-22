package com.moneybug.bug.users.vo;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Getter
@Setter
public class AccountVO implements UserDetails {

    private Long memNo;
    private String username;
    private String name;
    private String password;
    private String email;
    private String roles;

    private String emailValid;
    private String contact;
    private String addr;
    private String post;
    private String addrDetail;
    private boolean alertYn;
    private String attachNo;

    private boolean userAgreeYn;
    private boolean infoAgreeYn;
    private boolean snsAgreeYn;
    private boolean emailAgreeYn;
    private boolean workAlertYn;
    private boolean workConfAlertYn;
    private boolean hireAlertYn;
    private boolean applyAlertYn;
    private boolean userYn;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
