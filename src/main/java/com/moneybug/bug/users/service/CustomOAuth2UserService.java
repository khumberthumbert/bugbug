package com.moneybug.bug.users.service;

import com.moneybug.bug.security.dto.CustomOAuth2User;
import com.moneybug.bug.security.dto.GoogleResponse;
import com.moneybug.bug.security.dto.NaverResponse;
import com.moneybug.bug.security.dto.OAuth2Response;
import com.moneybug.bug.users.mapper.AccountMapper;
import com.moneybug.bug.users.vo.AccountVO;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final AccountMapper accountMapper;

    public CustomOAuth2UserService(AccountMapper accountMapper) {
        this.accountMapper = accountMapper;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);
        log.info("oAuth2User.getAttributes : {} ", oAuth2User.getAttributes());

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        OAuth2Response oAuth2Response = null;
        if (registrationId.equals("naver")) {

            oAuth2Response = new NaverResponse(oAuth2User.getAttributes());
        } else if (registrationId.equals("google")) {
            oAuth2Response = new GoogleResponse(oAuth2User.getAttributes());

        } else {
            return null;
        }
        String username = oAuth2Response.getProvider() + " " + oAuth2Response.getProviderId();
        AccountVO existData = accountMapper.findByUsername(username);

        String role = null;
        if (existData == null) {

            AccountVO accountVO = new AccountVO();
            accountVO.setUsername(username);
            accountVO.setEmail(oAuth2Response.getEmail());
            accountVO.setRoles("ROLE_USER");

            accountMapper.accountSave(accountVO);
        } else {
            existData.setUsername(username);
            existData.setEmail(oAuth2Response.getEmail());

            role = existData.getRoles();
            accountMapper.accountSave(existData);
        }

        return new CustomOAuth2User(oAuth2Response, role);
    }
}
