package com.moneybug.bug.users.service;

import com.moneybug.bug.users.domain.JoinDto;
import com.moneybug.bug.users.entity.Account;
import com.moneybug.bug.users.mapper.AccountMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class JoinService {

    private final AccountMapper accountMapper;
    private final PasswordEncoder passwordEncoder;

    public void joinProcess(JoinDto joinDto) {

        //db에 이미 동일한 username을 가진 회원이 존재하는가? -> username unique
        boolean isUser = accountMapper.existByUsernames(joinDto.getUsername());
        if (isUser) {
            log.info("{} 은 이미 존재 합니다.", joinDto.getUsername());
            return;
        }

        Account account = new Account();
        account.setUsername(joinDto.getUsername());
        account.setPassword(passwordEncoder.encode(joinDto.getPassword()));
        account.setEmail(joinDto.getEmail());
        account.setName(joinDto.getName());
        account.setContact(joinDto.getContact());
        account.setRoles(joinDto.getRoles()); //이건 나중에 체크박스 만들고 활성화 하기.

        accountMapper.accountSave(account);
    }
}
