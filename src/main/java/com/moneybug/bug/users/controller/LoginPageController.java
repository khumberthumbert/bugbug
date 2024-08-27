package com.moneybug.bug.users.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginPageController {



    /**
     * 로그인 페이지 + 카카오 API 넘겨주기.
     */
    @GetMapping("/login")
    public String loginPage(@RequestParam(value = "error", required = false) String error,
                        Model model, HttpSession session) {
        if (error != null) {
            Exception exception = (Exception) session.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
            String errorMessage = exception != null ? exception.getMessage() : "Unknown error";
            model.addAttribute("errorMessage", errorMessage);
        }
        return "account/loginPage";
    }
}
