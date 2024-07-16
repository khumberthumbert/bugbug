package com.moneybug.bug.users.service;

import com.moneybug.bug.users.entity.Account;
import com.moneybug.bug.users.mapper.AccountMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final PasswordEncoder passwordEncoder;
    private final AccountMapper accountMapper;

    public List<Account> getAccountList() {
        return accountMapper.getAcoountList();
    }

    public Account getAccountByMemNo(Long memNo) {
        return accountMapper.getAccountByMemNo(memNo);
    }

    /*public Account getAccountByEmail(String email) {
        return accountMapper.getAccountByEmail(email);
    }*/

    public Account getAccountByUsername(String username) {
        return accountMapper.getAccountUsername(username);
    }

    public void signup(Account account) {
        if (!account.getName().isEmpty() && !account.getEmail().isEmpty()) {
            account.setPassword(passwordEncoder.encode(account.getPassword()));
            accountMapper.insertAccount(account);
        }
    }

    /* 회원탈퇴 */
    public void withdraw(Long memNo) {
        accountMapper.deleteAccount(memNo);
    }
}
