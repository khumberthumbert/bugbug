package com.moneybug.bug.users.service;

import com.moneybug.bug.users.dto.CustomUserDetails;
import com.moneybug.bug.users.vo.AccountVO;
import com.moneybug.bug.users.mapper.AccountMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final AccountMapper accountMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        AccountVO accountVOData = accountMapper.findByUsername(username);
        if (accountVOData != null) {
            return new CustomUserDetails(accountVOData);
        } else {
            throw new UsernameNotFoundException("User not found");
        }
    }
}
