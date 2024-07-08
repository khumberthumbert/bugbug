package com.moneybug.bug.users.controller;

import com.moneybug.bug.users.domain.AccountDto;
import com.moneybug.bug.users.entity.Account;
import com.moneybug.bug.users.mapper.AccountMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final PasswordEncoder passwordEncoder;
    private final AccountMapper accountMapper;

    @PostMapping("/signup")
    public String signup(AccountDto accountDto) {
        ModelMapper mapper = new ModelMapper();
        Account account = mapper.map(accountDto, Account.class);
        account.setPassword(passwordEncoder.encode(accountDto.getPassword()));

        accountMapper.insert(account);

        return "redirect:/login";
    }
}
