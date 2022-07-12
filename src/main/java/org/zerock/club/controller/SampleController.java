package org.zerock.club.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Log4j2
@Controller
@RequestMapping("/sample")
public class SampleController {

    //로그인을 하지 않아도 접근 가능
    @GetMapping("/all")
    public void exAll() {
        log.info("exAll...");
    }

    //로그인한 사용자만 접근 가능
    @GetMapping("/member")
    public void exMember() {
        log.info("exMember...");
    }

    //어드민만 접근 가능
    @GetMapping("/admin")
    public void exAdmin() {
        log.info("exAdmin...");
    }

    //소셜 로그인 성공
    @GetMapping("/hello")
    public void hello() {
        log.info("hello...");
    }
}
