package com.crowdsourcing.test.controller;

import com.crowdsourcing.test.domain.User;
import com.crowdsourcing.test.domain.UserId;
import com.crowdsourcing.test.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;

    /**
     * 관리자 페이지 접속시
     */
    @GetMapping("/admin")
    public String list() {
        return "admin/adminList";
    }

    /**
     * 관리자 페이지 접속시
     */
    @PostMapping("/admin")
    public String paging(@RequestParam Map<String, Object> param, @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable, Model model) {
        String search = param.get("search").toString() , role = param.get("types").toString();
        Page<User> user;
        if(!StringUtils.hasText(search)) {
            search = "";
        }
        if(!StringUtils.hasText(role) || role.equals("none")) {
            role = "";
            user = userService.findByUsernameContaining(search, pageable);
        } else {
            user = userService.findByUsernameContainingAndRoleEquals(search, role, pageable);
        }
        int startPage = 1, endPage;
        int totalPages = user.getTotalPages();
        if(totalPages == 0) {
            endPage = 1;
        } else {
            endPage = totalPages;
        }
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("admin", user);

        return "admin/adminList :: #adminList";
    }

    /**
     * 사용자 삭제 버튼 클릭시
     */
    @PostMapping("/admin/delete")
    public String deleteBoard(@RequestParam("sList[]") List<String> selectedValues) {
        System.out.println(selectedValues);
        for(String i : selectedValues) {
            System.out.println(i);
        }
        for (String user : selectedValues) {
            String[] userOne = user.split("_");
            User one = userService.findOne(new UserId(Long.parseLong(userOne[0]), userOne[1]));
            userService.remove(one);
        }
        return "admin/adminList";
    }

    /**
     * 접근 권한이 없는 페이지에 접속했을시
     */
    @GetMapping("/denied")
    public String deniedAlert(HttpServletResponse response) throws Exception {
        response.setContentType("text/html; charset=euc-kr");
        PrintWriter out = response.getWriter();
        out.println("<script>alert('접근하실 수 없는 페이지입니다^^'); </script>");
        out.flush();

        return "home";
    }
}
