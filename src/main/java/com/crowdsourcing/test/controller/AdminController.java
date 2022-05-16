package com.crowdsourcing.test.controller;

import com.crowdsourcing.test.domain.CommonCode;
import com.crowdsourcing.test.domain.CommonCodeId;
import com.crowdsourcing.test.domain.BoardUser;
import com.crowdsourcing.test.service.CommonCodeService;
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

@Controller /** controller 클래스 어노테이션 */
@RequiredArgsConstructor /** final이나 @NonNull인 필드 값만 파라미터로 받는 생성자를 추가 */
public class AdminController {

    private final UserService userService;
    private final CommonCodeService commonCodeService;

    /**
     * 관리자 페이지 접속시
     */
    @GetMapping("/admin")
    public String adminList(Model model) {
        model.addAttribute("commonCodeList", commonCodeService.findByGroupCode("G002"));
        model.addAttribute("sizeList", commonCodeService.findByGroupCode("G004"));
        return "admin/adminList";
    }

    /**
     * 관리자 페이지 접속시
     */
    @PostMapping("/admin")
    public String adminList(@RequestParam Map<String, Object> param, @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable, Model model) {
        String search = param.get("search").toString();
        String types = param.get("types").toString();
        Page<BoardUser> user;
        if(!StringUtils.hasText(search)) {
            search = "";
        }
        if(!StringUtils.hasText(types) || types.equals("none")) {
            types = "";
            user = userService.findByUsernameContaining(search, pageable);
        } else {
            String[] common = types.split("_");
            CommonCode commonCode = commonCodeService.findById(new CommonCodeId(common[0],common[1]));
            user = userService.findByUsernameContainingAndRoleEquals(search, commonCode, pageable);
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

        return "admin/adminList :: #viewList";
    }

    /**
     * 사용자 삭제 버튼 클릭시
     */
    @PostMapping("/admin/delete")
    public String deleteUsers(@RequestParam("sList[]") List<String> selectedValues) {
        userService.deleteUsers(selectedValues);
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
