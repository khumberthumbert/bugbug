package com.moneybug.bug.users.service;

import com.moneybug.bug.users.mapper.PersistentLoginMapper;
import com.moneybug.bug.users.vo.PersistentLogin;
import lombok.RequiredArgsConstructor;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
@RequiredArgsConstructor
public class TokenRepositoryImpl implements PersistentTokenRepository {

    private final PersistentLoginMapper persistentLoginMapper;

    @Override
    public void createNewToken(PersistentRememberMeToken token) {
        persistentLoginMapper.createNewToken(token.getUsername(), token.getSeries(), token.getTokenValue(), new java.sql.Timestamp(token.getDate().getTime()));
    }

    @Override
    public void updateToken(String series, String tokenValue, Date lastUsed) {
        persistentLoginMapper.updateToken(series, tokenValue, new java.sql.Timestamp(lastUsed.getTime()));
    }

    @Override
    public PersistentRememberMeToken getTokenForSeries(String seriesId) {
        PersistentLogin login = persistentLoginMapper.getTokenForSeries(seriesId);
        return login == null ? null : new PersistentRememberMeToken(login.getUsername(), login.getSeries(), login.getToken(), new Date(login.getLastUsed().getTime()));
    }

    @Override
    public void removeUserTokens(String username) {
        persistentLoginMapper.removeUserTokens(username);
    }
}
