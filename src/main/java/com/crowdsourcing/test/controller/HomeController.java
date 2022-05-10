package com.crowdsourcing.test.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller /** controller 클래스 어노테이션 */
@RequiredArgsConstructor /** final이나 @NonNull인 필드 값만 파라미터로 받는 생성자를 추가 */
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
