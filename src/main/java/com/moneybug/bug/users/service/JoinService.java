package com.moneybug.bug.users.service;

import com.moneybug.bug.users.dto.JoinDTO;
import com.moneybug.bug.users.vo.AccountVO;
import com.moneybug.bug.users.mapper.AccountMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

@Service
@Log4j2
@RequiredArgsConstructor
public class JoinService {

    private final AccountMapper accountMapper;
    private final PasswordEncoder passwordEncoder;

    // ConcurrentHashMap을 사용하여 유저네임을 캐싱합니다.
    private final ConcurrentHashMap<String, Boolean> userExistenceCache = new ConcurrentHashMap<>();

    public void joinProcess(JoinDTO joinDto) {

        // 캐시에 해당 유저가 있는지 확인
        Boolean isUser = userExistenceCache.get(joinDto.getUsername());

        if (isUser == null) {
            // 캐시에 없으면 DB에서 조회 후 캐시에 저장
            isUser = accountMapper.existByUsernames(joinDto.getUsername());
            userExistenceCache.put(joinDto.getUsername(), isUser);
        }

        // 유저가 이미 존재하면 로깅 후 종료
        if (isUser) {
            log.info("{} 은 이미 존재 합니다.", joinDto.getUsername());
            return;
        }

        // 회원가입 정보 저장
        AccountVO accountVO = new AccountVO();
        accountVO.setUsername(joinDto.getUsername());
        accountVO.setPassword(passwordEncoder.encode(joinDto.getPassword()));
        accountVO.setEmail(joinDto.getEmail());
        accountVO.setName(joinDto.getName());
        accountVO.setContact(joinDto.getContact());
        accountVO.setRoles(joinDto.getRoles());

        accountMapper.accountSave(accountVO);

        // 회원가입 완료 후 캐시에서 해당 유저네임 제거 (다음 번 캐시 업데이트를 위해)
        userExistenceCache.remove(joinDto.getUsername());
    }
}
