package com.crowdsourcing.test.controller;

import com.crowdsourcing.test.controller.form.BoardSearch;
import com.crowdsourcing.test.controller.form.SelectedForm;
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

@Controller
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;

    /**
     * 관리자 페이지 접속시
     */
//    @GetMapping("/admin")
//    public String list(@ModelAttribute("userSearch") BoardSearch userSearch, Model model, SelectedForm selected) {
//        List<User> user = userService.findUser(userSearch);
//        model.addAttribute("user", user);
//        model.addAttribute("selected", selected);
//        return "admin/adminList";
//    }
    @GetMapping("/admin")
    public String list(@ModelAttribute("userSearch") BoardSearch userSearch, Model model,
                       @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                       SelectedForm selected) {
        String search = userSearch.getSearch();
        String role = userSearch.getTypes();
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
        int startPage = Math.max(1, user.getPageable().getPageNumber() - 5);
        int endPage = Math.min(user.getTotalPages(), user.getPageable().getPageNumber() + 5);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("user", user);
        model.addAttribute("selected", selected);
        return "admin/adminList";
    }

    /**
     * 사용자 삭제 버튼 클릭시
     */
    @PostMapping("/admin")
    public String deleteUser(@ModelAttribute("selected") SelectedForm selectedForm){
        List<String> admin = selectedForm.getSelectedUser();
        for(String user : admin) {
            String[] userOne = user.split("_");
            User one = userService.findOne(new UserId(Long.parseLong(userOne[0]), userOne[1]));
            userService.remove(one);
        }

        return "redirect:/admin";
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
