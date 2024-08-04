package com.moneybug.bug.users.service;

import com.moneybug.bug.users.dto.JoinDTO;
import com.moneybug.bug.users.vo.AccountVO;
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

    public void joinProcess(JoinDTO joinDto) {

        //db에 이미 동일한 username을 가진 회원이 존재하는가? -> username unique
        boolean isUser = accountMapper.existByUsernames(joinDto.getUsername());
        if (isUser) {
            log.info("{} 은 이미 존재 합니다.", joinDto.getUsername());
            return;
        }

        AccountVO accountVO = new AccountVO();
        accountVO.setUsername(joinDto.getUsername());
        accountVO.setPassword(passwordEncoder.encode(joinDto.getPassword()));
        accountVO.setEmail(joinDto.getEmail());
        accountVO.setName(joinDto.getName());
        accountVO.setContact(joinDto.getContact());
        accountVO.setRoles(joinDto.getRoles());

        accountMapper.accountSave(accountVO);
    }


}
