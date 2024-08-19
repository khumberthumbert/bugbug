package com.moneybug.bug.users.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginPageController {



    /**
     * 로그인 페이지 + 카카오 API 넘겨주기.
     */
    @GetMapping("/login")
    public String loginPage(Model model) {
        return "account/loginPage";
    }
}
