package com.moneybug.bug.security.dto;

import java.util.Map;

public class KakaoResponse implements OAuth2Response {

    private final String providerId;
    private final String email;
    private final String name;

    public KakaoResponse(Map<String, Object> attribute) {
        // 카카오 API 응답에서 필요한 정보 추출
        Map<String, Object> kakaoAccount = (Map<String, Object>) attribute.get("kakao_account");
        Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");

        // 카카오 사용자 ID를 문자열로 변환하여 providerId에 저장
        this.providerId = String.valueOf(attribute.get("id"));

        // 이메일과 닉네임 정보 추출
        this.email = (String) kakaoAccount.get("email");  // "account_email" 사용
        this.name = (String) profile.get("nickname");  // "profile_nickname" 사용
    }

    @Override
    public String getProvider() {
        return "kakao";
    }

    @Override
    public String getProviderId() {
        return providerId;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public String getName() {
        return name;
    }
}
