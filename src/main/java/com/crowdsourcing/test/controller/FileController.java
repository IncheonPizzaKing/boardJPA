package com.crowdsourcing.test.controller;

import com.crowdsourcing.test.controller.form.BoardSearch;
import com.crowdsourcing.test.domain.File;
import com.crowdsourcing.test.service.CommonCodeService;
import com.crowdsourcing.test.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;
    private final CommonCodeService commonCodeService;

    /**
     * 파일 조회 페이지 접속시
     */
    @GetMapping("/file")
    public String list(Model model) {
        model.addAttribute("fileUseList", commonCodeService.findByGroupCode("G003"));
        model.addAttribute("sizeList", commonCodeService.findByGroupCode("G004"));
        return "admin/fileList";
    }

    /**
     * 파일 조회 페이지 접속시
     */
    @PostMapping("/file")
    public String paging(@RequestParam Map<String, Object> param, @PageableDefault(sort = "file_id", direction = Sort.Direction.DESC) Pageable pageable, Model model) {
        BoardSearch fileSearch = new BoardSearch();
        if (param.get("types") != null) {
            fileSearch.setTypes(param.get("types").toString());
        }
        if (param.get("search") != null) {
            fileSearch.setSearch(param.get("search").toString());
        }
        Page<File> file = fileService.findFile(fileSearch, pageable);

        int startPage = 1, endPage;
        int totalPages = file.getTotalPages();
        if(totalPages == 0) {
            endPage = 1;
        } else {
            endPage = totalPages;
        }
        model.addAttribute("file", file);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        return "admin/fileList :: #viewList";
    }

    /**
     * 파일 다운로드 버튼 클릭시
     */
    @GetMapping("/download/{file}")
    public ResponseEntity<Resource> downloadFile(@PathVariable("file") Long file) throws Exception {
        return fileService.downloadFile(file);
    }

    /**
     * 파일 삭제 버튼 클릭시
     */
    @PostMapping("/file/delete")
    public String deleteFile(@RequestParam("sList[]") List<Integer> selectedValues) {
        fileService.deleteFile(selectedValues);
        return "admin/fileList";
    }
}
