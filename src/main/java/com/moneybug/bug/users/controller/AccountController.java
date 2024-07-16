package com.moneybug.bug.users.controller;

import com.moneybug.bug.users.entity.Account;
import com.moneybug.bug.users.service.AccountService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @GetMapping("/")
    public String home(Model model) { //인증된 사용자의 정보를 보여줌
        //token에 저장되어 있는 인증된 사용자의 memNo값
        Long memNo = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Account account = accountService.getAccountByMemNo(memNo);
        account.setPassword(null);//password는 보이지 않도록 null 설정
        model.addAttribute("user", account);
        return "home/home";
    }

    /*@GetMapping("/userList")
    public String getUserList(Model model) { // User 테이블의 전체 정보를 보여줌.
        List<Account> accountList = accountService.getAccountList();
        model.addAttribute("list", accountList);
        return "accountListPage";
    }*/

    @GetMapping("/login")
    public String loginPage() { //로그인 되지 않은 상태이면 로그인 페이지를, 로그인된 상태이면 home
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken)
            return "login/loginPage";
        return "redirect:/";
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
