package com.moneybug.bug.security.dto;

public interface OAuth2Response {

    //ex : 네이버, 구글
    String getProvider();

    //ex : 번호에 대한 값.
    String getProviderId();

    //사용자 설정 이메일
    String getEmail();

    String getName();
}
