package com.crowdsourcing.test.controller;

import com.crowdsourcing.test.controller.form.UserForm;
import com.crowdsourcing.test.domain.CommonCode;
import com.crowdsourcing.test.domain.CommonCodeId;
import com.crowdsourcing.test.domain.User;
import com.crowdsourcing.test.service.CommonCodeService;
import com.crowdsourcing.test.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final CommonCodeService commonCodeService;

    /**
     * 회원가입 페이지 접속시
     */
    @GetMapping("/user/new")
    public String createUserForm(Model model) {
        UserForm user = new UserForm();
        user.setCommonCodeList(commonCodeService.findByGroupCode("G002"));
        model.addAttribute("userForm", user);
        return "user/createUserForm :: #modalForm";
    }

    /**
     * 회원가입 버튼 클릭시
     */
    @PostMapping("/user/new")
    public String create(@Valid UserForm userForm, BindingResult result) {
        if (result.hasErrors()) {
            return "user/createUserForm";
        }
        User user = new User();
        user.setUsername(userForm.getUsername());
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(userForm.getPassword()));
        String[] commonCodeOne = userForm.getCommonCodeId().split("_");
        CommonCode one = commonCodeService.findById(new CommonCodeId(commonCodeOne[0], commonCodeOne[1]));
        user.setCommonCode(one);
        userService.save(user);

        return "redirect:/";
    }
}