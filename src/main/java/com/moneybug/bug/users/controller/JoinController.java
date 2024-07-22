package com.moneybug.bug.users.controller;

import com.moneybug.bug.users.dto.JoinDTO;
import com.moneybug.bug.users.service.JoinService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Log4j2
@RequiredArgsConstructor
public class JoinController {

    private final JoinService joinService;

    @GetMapping("/join")
    public String signup() {
        return "account/signupPage";
    }

    @PostMapping("/joinProc")
    public String signupProcess(JoinDTO joinDto) {
        System.out.println(joinDto.getUsername());
        log.info("{}", joinDto.getUsername());

        joinService.joinProcess(joinDto);

        return "redirect:/login";
    }
}
