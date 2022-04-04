package com.crowdsourcing.test.controller;

import com.crowdsourcing.test.domain.User;
import com.crowdsourcing.test.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

//    @GetMapping("/user/login")
//    public String createLoginForm(Model model) {
//        return "user/loginForm";
//    }

    @GetMapping("/user/new")
    public String createUserForm(Model model) {
        model.addAttribute("userForm", new UserForm());
        return "user/createUserForm";
    }

    @PostMapping("/user/new")
    public String create(@ModelAttribute("userForm") UserForm userForm, BindingResult result) {
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

//    @GetMapping("/user/logout")
//    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
//        new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder.getContext().getAuthentication());
//        return "redirect:/user/login";
//    }
}
