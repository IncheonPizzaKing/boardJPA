package com.crowdsourcing.test.controller;

import com.crowdsourcing.test.controller.form.BoardSearch;
import com.crowdsourcing.test.controller.form.CommonCodeForm;
import com.crowdsourcing.test.domain.CommonCode;
import com.crowdsourcing.test.domain.CommonCodeId;
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

import javax.validation.Valid;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class CommonCodeController {

    private final CommonGroupService commonGroupService;
    private final CommonCodeService commonCodeService;

    /**
     * 공통코드 작성 페이지 접속시
     */
    @GetMapping("/commonCode/new")
    public String createForm(Model model) {
        CommonCodeForm commonCodeForm = new CommonCodeForm();
        commonCodeForm.setCommonGroupList(commonGroupService.findAll());
        model.addAttribute("commonCodeForm", commonCodeForm);
        return "admin/createCommonCode";
    }

    /**
     * 공통코드 작성 버튼 클릭시
     */
    @PostMapping("/commonCode/new")
    public String create(@ModelAttribute("commonCodeForm") CommonCodeForm commonCodeForm, BindingResult result) throws Exception {

        if (result.hasErrors()) {
            return "admin/createCommonCode";
        }
        commonCodeService.save(commonCodeForm);
        return "redirect:/commonCode";
    }

    /**
     * 공통코드 조회시
     */
    @GetMapping("/commonCode")
    public String list(Model model) {
        model.addAttribute("commonGroupList", commonGroupService.findAll());
        return "admin/commonCodeList";
    }

    /**
     * 공통코드 검색시
     */
    @PostMapping("/commonCode")
    public String paging(@RequestParam Map<String, Object> param, Model model, @PageableDefault(size = 10, sort = "code", direction = Sort.Direction.DESC) Pageable pageable) {
        BoardSearch commonCodeSearch = new BoardSearch();
        if (param.get("types") != null) {
            commonCodeSearch.setTypes(param.get("types").toString());
        }
        if (param.get("search") != null) {
            commonCodeSearch.setSearch(param.get("search").toString());
        }
        Page<CommonCode> commonCodeList = commonCodeService.findCommonCode(commonCodeSearch, pageable);
        int startPage = 1, endPage;
        int totalPages = commonCodeList.getTotalPages();
        if (totalPages == 0) {
            endPage = 1;
        } else {
            endPage = totalPages;
        }
        model.addAttribute("commonCode", commonCodeList);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "admin/commonCodeList :: #commonCodeList";
    }

    /**
     * 공통코드 수정페이지 접속시
     */
    @GetMapping("/commonCode/{code}/update")
    public String updateCommonCodeForm(@PathVariable("code") String code, Model model) {

        String codeOne[] = code.split("_");
        CommonCode commonCode = (CommonCode) commonCodeService.findById(new CommonCodeId(codeOne[0], codeOne[1]));
        CommonCodeForm form = CommonCodeForm.builder()
                .code(commonCode.getCode())
                .codeName(commonCode.getCodeName())
                .codeNameKor(commonCode.getCodeNameKor())
                .commonGroup(commonCode.getCommonGroup())
                .use(commonCode.isUse())
                .description(commonCode.getDescription())
                .build();
        model.addAttribute("form", form);
        return "admin/updateCommonCode";
    }


    /**
     * 공통코드 수정 버튼 클릭시
     */
    @PostMapping("/commonCode/{code}/update")
    public String updateCommonCode(@ModelAttribute("form") CommonCodeForm form, @PathVariable String code) {
        commonCodeService.update(code, form.getUse(), form.getDescription());
        return "redirect:/commonCode";
    }


    /**
     * 공통코드 삭제 버튼 클릭시
     */
    @GetMapping("/commonCode/{code}/delete")
    public String deleteCommonCode(@PathVariable String code) {
        commonCodeService.remove(code);
        return "redirect:/commonCode";
    }
}
