package com.moneybug.bug.security.provider;

import com.moneybug.bug.users.entity.Account;
import com.moneybug.bug.users.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component("authenticationProvider")
@RequiredArgsConstructor
public class FormAuthenticationProvider implements AuthenticationProvider {

    private final AccountService accountService;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = (String) authentication.getPrincipal(); //로그인 창에 입력한 username
        String password = (String) authentication.getCredentials(); // 로그인 창에 입력한 password

        UsernamePasswordAuthenticationToken token;
        Account account = accountService.getAccountByUsername(username);

        //일치하는 account 정보 있는지 확인
        if (account != null && passwordEncoder.matches(password, account.getPassword())) {
            List<GrantedAuthority> roles = new ArrayList<>();
            roles.add(new SimpleGrantedAuthority("USER")); // 권한부여

            //인증된 account 정보를 담아 SecurityContextHolder에 저장되는 token
            token = new UsernamePasswordAuthenticationToken(account.getMemNo(), null, roles);

            return token;
        }
        /*
        Exception을 던지지 않고 다른 값을 반환하면 authenticate() 메서드는 정상적으로 실행된 것이므로
        인증되지 않았다면 Exception을 throw 해야 한다.
         */
        throw new BadCredentialsException("No such account or wrong password.");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
