package com.crowdsourcing.test.controller;

import com.crowdsourcing.test.dto.SearchDto;
import com.crowdsourcing.test.dto.commoncode.CommonCodeDto;
import com.crowdsourcing.test.domain.CommonCode;
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
public class CommonCodeController {

    private final CommonGroupService commonGroupService;
    private final CommonCodeService commonCodeService;

    /**
     * 공통코드 작성 페이지 접속시
     */
    @GetMapping("/commonCode/new")
    public String createCommonCodeDto(Model model) {
        model.addAttribute("commonCodeDto", new CommonCodeDto());
        model.addAttribute("commonGroupList", commonGroupService.findAll());
        return "admin/createCommonCode :: #modalForm";
    }

    /**
     * 공통코드 작성 버튼 클릭시
     */
    @PostMapping("/commonCode/new")
    public String createCommonCode(@ModelAttribute("commonCodeDto") CommonCodeDto commonCodeDto, BindingResult result) throws Exception {

        if (result.hasErrors()) {
            return "admin/createCommonCode";
        }
        commonCodeService.save(commonCodeDto);
        return "redirect:/commonCode";
    }

    /**
     * 공통코드 조회시
     */
    @GetMapping("/commonCode")
    public String commonCodeList(Model model) {
        model.addAttribute("commonGroupList", commonGroupService.findAll());
        model.addAttribute("sizeList", commonCodeService.findByGroupCode("G004"));
        return "admin/commonCodeList";
    }

    /**
     * 공통코드 검색시
     */
    @PostMapping("/commonCode")
    public String commonCodeList(@RequestParam Map<String, Object> param, Model model, @PageableDefault(size = 10, sort = "code", direction = Sort.Direction.DESC) Pageable pageable) {
        SearchDto commonCodeSearch = new SearchDto();
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

        return "admin/commonCodeList :: #viewList";
    }

    /**
     * 공통코드 수정페이지 접속시
     */
    @GetMapping("/commonCode/{code}/update")
    public String updateCommonCodeDto(@PathVariable("code") String code, Model model) {

        CommonCodeDto form = commonCodeService.updateCommonCodeDto(code);
        model.addAttribute("form", form);
        return "admin/updateCommonCode :: #modalForm";
    }


    /**
     * 공통코드 수정 버튼 클릭시
     */
    @PostMapping("/commonCode/{code}/update")
    public String updateCommonCode(@ModelAttribute("form") CommonCodeDto form, @PathVariable String code) {
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
