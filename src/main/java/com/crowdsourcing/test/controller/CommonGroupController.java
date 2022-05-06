package com.crowdsourcing.test.controller;

import com.crowdsourcing.test.controller.form.BoardSearch;
import com.crowdsourcing.test.controller.form.CommonGroupForm;
import com.crowdsourcing.test.domain.CommonGroup;
import com.crowdsourcing.test.service.CommonCodeService;
import com.crowdsourcing.test.service.CommonGroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller /** controller 클래스 어노테이션 */
@RequiredArgsConstructor /** final이나 @NonNull인 필드 값만 파라미터로 받는 생성자를 추가 */
public class CommonGroupController {

    private final CommonCodeService commonCodeService;
    private final CommonGroupService commonGroupService;

    /**
     * 공통그룹 작성 페이지 접속시
     */
    @GetMapping("/commonGroup/new")
    public String createCommonGroupForm(Model model) {
        model.addAttribute("commonGroupForm", new CommonGroupForm());
        return "admin/createCommonGroup";
    }

    /**
     * 공통그룹 작성 버튼 클릭시
     */
    @PostMapping("/commonGroup/new")
    public String createCommonGroup(@ModelAttribute("commonGroupForm") CommonGroupForm commonGroupForm, BindingResult result) throws Exception {

        if (result.hasErrors()) {
            return "admin/createCommonGroup";
        }
        commonGroupService.save(commonGroupForm);
        return "redirect:/commonGroup";
    }

    /**
     * 공통그룹 조회시
     */
    @GetMapping("/commonGroup")
    public String commonGroupList(Model model) {
        model.addAttribute("sizeList", commonCodeService.findByGroupCode("G004"));
        return "admin/commonGroupList";
    }

    /**
     * 공통그룹 검색시
     */
    @PostMapping("/commonGroup")
    public String commonGroupList(@RequestParam Map<String, Object> param, Model model, @PageableDefault(size = 10, sort = "groupCode", direction = Sort.Direction.DESC) Pageable pageable) {
        BoardSearch commonGroupSearch = new BoardSearch();
        if (param.get("search") != null) {
            commonGroupSearch.setSearch(param.get("search").toString());
        }
        Page<CommonGroup> commonGroupList = commonGroupService.findCommonGroup(commonGroupSearch, pageable);
        int startPage = 1, endPage;
        int totalPages = commonGroupList.getTotalPages();
        if (totalPages == 0) {
            endPage = 1;
        } else {
            endPage = totalPages;
        }
        model.addAttribute("commonGroup", commonGroupList);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "admin/commonGroupList :: #viewList";
    }

    /**
     * 공통그룹 수정페이지 접속시
     */
    @GetMapping("/commonGroup/{code}/update")
    public String updateCommonGroupForm(@PathVariable("code") String code, Model model) {
        CommonGroupForm form = commonGroupService.updateCommonGroupForm(code);
        model.addAttribute("form", form);
        return "admin/updateCommonGroup :: #modalForm";
    }


    /**
     * 공통그룹 수정 버튼 클릭시
     */
    @PostMapping("/commonGroup/{code}/update")
    public String updateCommonGroup(@ModelAttribute("form") CommonGroupForm form, @PathVariable String code) {
        commonGroupService.update(code, form.getIsUse(), form.getDescription());
        return "redirect:/commonGroup";
    }


    /**
     * 공통그룹 삭제 버튼 클릭시
     */
    @GetMapping("/commonGroup/{code}/delete")
    public String deleteCommonGroup(@PathVariable String code) {
        commonGroupService.remove(code);
        return "redirect:/commonGroup";
    }
}
