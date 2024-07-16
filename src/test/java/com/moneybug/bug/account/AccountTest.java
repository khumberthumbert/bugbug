package com.moneybug.bug.account;


import com.moneybug.bug.users.entity.Account;
import com.moneybug.bug.users.mapper.AccountMapper;
import com.moneybug.bug.users.service.AccountService;
import org.junit.jupiter.api.Test;


public class AccountTest {

    AccountMapper accountMapper;
    AccountService accountService;


    @Test
    public void 회원가입() throws Exception {
        //given
        Account account = new Account();
        account.setUsername("KUH");
        account.setPassword("1234");
        account.setEmail("dnrgks142@gmail.com");

        //when



        //then
    }
}
