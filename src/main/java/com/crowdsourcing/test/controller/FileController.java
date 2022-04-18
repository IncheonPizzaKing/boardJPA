package com.crowdsourcing.test.controller;

import com.crowdsourcing.test.controller.form.BoardSearch;
import com.crowdsourcing.test.controller.form.SelectedForm;
import com.crowdsourcing.test.domain.File;
import com.crowdsourcing.test.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    /**
     * 관리자 페이지 접속시
     */
    @GetMapping("/file")
    public String list(@ModelAttribute("fileSearch") BoardSearch fileSearch, Model model, SelectedForm selectedForm,
                       @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<File> file = fileService.findFile(fileSearch, pageable);
        int startPage = 1, endPage;
        int totalPages = file.getTotalPages();
        if(totalPages == 0) {
            endPage = 1;
        }
        else {
            endPage = totalPages;
        }
        model.addAttribute("file", file);
        model.addAttribute("selected", selectedForm);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        return "admin/fileList";
    }

    @GetMapping("/download/{file}")
    public ResponseEntity<Resource> downloadFile(@PathVariable("file") Long file) throws Exception {
        com.crowdsourcing.test.domain.File fileDto = fileService.findById(file);
        Path path = Paths.get(fileDto.getFilePath());
        Resource resource = new InputStreamResource(Files.newInputStream(path));
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment;fileName=\\" + new String(fileDto.getOriginFileName().getBytes("UTF-8"), "ISO-8859-1"))
                .body(resource);
    }

    /**
     * 사용자 삭제 버튼 클릭시
     */
    @PostMapping("/file")
    public String deleteBoard(@ModelAttribute("selected") SelectedForm selected){
        List<String> list = selected.getSelectedUser();
        for(String file : list) {
            Long fileId = Long.parseLong(file);
            File findFile = fileService.findById(fileId);
            fileService.deleteFile(findFile);
        }

        return "redirect:/file";
    }
}
