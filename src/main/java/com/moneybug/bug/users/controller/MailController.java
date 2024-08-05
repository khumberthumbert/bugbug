package com.moneybug.bug.users.controller;

import com.moneybug.bug.users.service.MailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@Log4j2
public class MailController {

    private final MailService mailService;

    @ResponseBody
    @PostMapping("/mail")
    public Map<String, Object> MailSend(@RequestBody Map<String, String> request) {
        String mail = request.get("mail");
        log.info("mail 뭘까? {}", mail);
        int number = mailService.sendMail(mail);
        log.info("number 뭘까? {}", number);

        Map<String, Object> response = new HashMap<>();
        response.put("success", true); // 항상 true 반환 (테스트용)
        response.put("number", number);
        log.info("response.get(success) 뭘까? {}", response.get("success"));
        log.info("response.get(number) 뭘까? {}", response.get("number"));

        return response;
    }

}