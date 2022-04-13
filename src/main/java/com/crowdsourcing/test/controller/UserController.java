package com.crowdsourcing.test.controller;

import com.crowdsourcing.test.controller.form.UserForm;
import com.crowdsourcing.test.domain.User;
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

    /**
     * 회원가입 페이지 접속시
     */
    @GetMapping("/user/new")
    public String createUserForm(Model model) {
        model.addAttribute("userForm", new UserForm());
        return "user/createUserForm";
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
        user.setRole(userForm.getRole());

        userService.save(user);

        return "redirect:/";
    }
}