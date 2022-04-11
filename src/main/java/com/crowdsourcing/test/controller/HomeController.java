package com.crowdsourcing.test.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
public class HomeController {

    /**
     * "/" 기본경로 접속시
     */
    @RequestMapping("/")
    public String home() {
        return "home";
    }
}
