package com.moneybug.bug.security.provider;

import com.moneybug.bug.users.domain.AccountDto;
import com.moneybug.bug.users.entity.Account;
import com.moneybug.bug.users.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userDetailsService")
@RequiredArgsConstructor
public class FormAccountDetailsService implements UserDetailsService {

    private final AccountService accountService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountService.getAccountByUsername(username);
        if (account == null) {
            throw new UsernameNotFoundException("No user found with username: " + username);
        }
        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(account.getRoles()));
        ModelMapper mapper = new ModelMapper();
        AccountDto accountDto = mapper.map(account, AccountDto.class);
        return new AccountContext(accountDto, authorities);

    }
}
