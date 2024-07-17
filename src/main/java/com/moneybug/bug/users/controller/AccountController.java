package com.moneybug.bug.users.controller;

import com.moneybug.bug.security.provider.FormAuthenticationProvider;
import com.moneybug.bug.users.entity.Account;
import com.moneybug.bug.users.service.AccountService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

/**
 * SecurityContextHolder : SecurityContext를 저장하는 기본 클래스
 * getContext() : 현재 스레드에 대한 'SecurityContext'를 반환 -> 현재 인증된 사용자에 대한 정보를 포함
 * getAuthentication() : securityContext에서 현재 사용자의 인증 정보를 반환한다. Authentication 객체는 현재 인증된 사용자에 대한 세부 정보를 포함
 * getPrincipal() : Authentication 객체에서 인증된 사용자의 주체(principal)를 반환한다. 주체는 사용자에 대한 세부 정보를 나타내며, 이름, ID, 사용자 객체 자체가 될 수 있다.
 */

@Controller
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;
    private final FormAuthenticationProvider formAuthenticationProvider;

    @GetMapping("/")
    public String home(Model model) { //인증된 사용자의 정보를 보여줌
        //token에 저장되어 있는 인증된 사용자의 memNo값
        String principal = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long memNo = Long.parseLong(principal);

        Account account = accountService.getAccountByMemNo(memNo);
        account.setPassword(null);//password는 보이지 않도록 null 설정
        model.addAttribute("user", account);
        return "home/home";
    }

    @GetMapping("/login")
    public String loginPage() { //로그인 되지 않은 상태이면 로그인 페이지를, 로그인된 상태이면 home
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken)
            return "login/loginPage";
        return "redirect:/";
    }

    @PostMapping("/auth")
    public String login(String username, String password, Model model) {
        try {
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
            Authentication authentication = formAuthenticationProvider.authenticate(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return "redirect:/";
        } catch (Exception e) {
            model.addAttribute("error", "Invalid username or password.");
            return "login/loginPage";
        }
    }

    @GetMapping("/signup")
    public String signupPage() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken)
            return "login/signupPage";
        return "redirect:/";
    }

    /* 회원 가입 */
    @PostMapping("/signup")
    public String signup(Account account) {
        try {
            accountService.signup(account);
        } catch (DuplicateKeyException e) {
            return "redirect:/signup?error_code=-1";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/signup?error_code=-99";
        }
        return "redirect:/login";
    }

    /* 회원 탈퇴*/
    @PostMapping("/delete")
    public String withdraw(HttpSession session) {
        Long memNo = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (memNo != null) {
            accountService.withdraw(memNo);
        }
        //SecurityContextHolder에 남아있는 token 삭제
        SecurityContextHolder.clearContext();
        return "redirect:/";
    }

}
