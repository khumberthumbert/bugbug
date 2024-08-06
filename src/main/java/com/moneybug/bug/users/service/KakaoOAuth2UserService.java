package com.moneybug.bug.users.service;

import com.moneybug.bug.users.dto.KakaoUserInfoResponseDto;
import com.moneybug.bug.users.mapper.AccountMapper;
import com.moneybug.bug.users.vo.AccountVO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class KakaoOAuth2UserService extends DefaultOAuth2UserService {

    private final KakaoService kakaoService;
    private final AccountMapper accountMapper;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        String accessToken = userRequest.getAccessToken().getTokenValue();
        KakaoUserInfoResponseDto userInfo = kakaoService.getUserInfo(accessToken);

        String kakaoId = userInfo.getId().toString();
        AccountVO account = accountMapper.findByUsername(kakaoId);

        if (account == null) {
            account = new AccountVO();
            account.setUsername(kakaoId);
            account.setName(userInfo.getKakaoAccount().getProfile().getNickName());
            accountMapper.accountSave(account);
        }

        // 사용자 속성을 설정
        Map<String, Object> attributes = new HashMap<>(oAuth2User.getAttributes());
        attributes.put("id", kakaoId);

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")),
                attributes,
                "id"
        );
    }
}
