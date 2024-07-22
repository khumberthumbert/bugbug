package com.moneybug.bug.account;


import com.moneybug.bug.users.vo.AccountVO;
import com.moneybug.bug.users.mapper.AccountMapper;
import org.junit.jupiter.api.Test;


public class AccountVOTest {

    AccountMapper accountMapper;


    @Test
    public void 회원가입() throws Exception {
        //given
        AccountVO accountVO = new AccountVO();
        accountVO.setUsername("KUH");
        accountVO.setPassword("1234");
        accountVO.setEmail("dnrgks142@gmail.com");

        //when



        //then
    }
}
