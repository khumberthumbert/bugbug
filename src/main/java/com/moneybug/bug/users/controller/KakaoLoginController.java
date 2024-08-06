package com.moneybug.bug.users.controller;

import com.moneybug.bug.users.dto.KakaoUserInfoResponseDto;
import com.moneybug.bug.users.mapper.AccountMapper;
import com.moneybug.bug.users.service.KakaoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RequiredArgsConstructor
@RestController
public class KakaoLoginController {

    private final KakaoService kakaoService;
    private final AccountMapper accountMapper;

    @GetMapping("/callback")
    public ResponseEntity<?> callback(@RequestParam("code") String code) {
        String accessToken = kakaoService.getAccessTokenFromKakao(code);
        KakaoUserInfoResponseDto userInfo = kakaoService.getUserInfo(accessToken);

        String kakaoId = String.valueOf(userInfo.getId());
        // User 로그인, 또는 회원가입 로직 추가

        // SecurityContext에 인증 정보를 설정합니다.
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("Authenticated User: {}", authentication.getName());

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
