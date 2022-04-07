package com.crowdsourcing.test.controller;

import com.crowdsourcing.test.domain.User;
import com.crowdsourcing.test.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;

    @GetMapping("/admin")
    public String list(@ModelAttribute("userSearch") BoardSearch userSearch, Model model) {
        List<User> user = userService.findUser(userSearch);
        model.addAttribute("user", user);
        model.addAttribute("selected", new AdminForm());
        return "admin/adminList";
    }

    @PostMapping("/admin")
    public String deleteBoard(@Valid AdminForm selected) {
        List<Long> list = selected.getId();

        for(int i = 0; i < list.size(); i++) {
            User data = userService.findOne(list.get(i));
            userService.remove(data);
        }

        return "redirect:/admin";
    }

    @GetMapping("/denied")
    public String deniedAlert(HttpServletResponse response) throws Exception {
        response.setContentType("text/html; charset=euc-kr");
        PrintWriter out = response.getWriter();
        out.println("<script>alert('접근하실 수 없는 페이지입니다^^'); </script>");
        out.flush();

        return "home";
    }
}
