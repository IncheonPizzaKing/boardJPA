package com.crowdsourcing.test.controller;

import com.crowdsourcing.test.controller.form.UserForm;
import com.crowdsourcing.test.service.CommonCodeService;
import com.crowdsourcing.test.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller /** controller 클래스 어노테이션 */
@RequiredArgsConstructor /** final이나 @NonNull인 필드 값만 파라미터로 받는 생성자를 추가 */
public class UserController {

    private final UserService userService;
    private final CommonCodeService commonCodeService;

    /**
     * 회원가입 페이지 접속시
     */
    @GetMapping("/user/new")
    public String createUserForm(Model model) {
        UserForm user = new UserForm();
        model.addAttribute("userForm", user);
        model.addAttribute("commonCodeList", commonCodeService.findByGroupCode("G002"));
        return "user/createUserForm :: #modalForm";
    }

    /**
     * 회원가입 버튼 클릭시
     */
    @PostMapping("/user/new")
    public String signup(@Valid UserForm userForm, BindingResult result) {
        if (result.hasErrors()) {
            return "user/createUserForm";
        }
        userService.signup(userForm);
        return "redirect:/";
    }
}