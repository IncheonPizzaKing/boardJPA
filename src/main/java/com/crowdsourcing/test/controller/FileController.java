package com.crowdsourcing.test.controller;

import com.crowdsourcing.test.controller.form.BoardSearch;
import com.crowdsourcing.test.controller.form.SelectedForm;
import com.crowdsourcing.test.domain.File;
import com.crowdsourcing.test.domain.User;
import com.crowdsourcing.test.domain.UserId;
import com.crowdsourcing.test.service.FileMasterService;
import com.crowdsourcing.test.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    /**
     * 관리자 페이지 접속시
     */
    @GetMapping("/file")
    public String list(@ModelAttribute("fileSearch") BoardSearch fileSearch, Model model, SelectedForm selectedForm) {
        List<File> file = fileService.findFile(fileSearch);
        model.addAttribute("file", file);
        model.addAttribute("selected", selectedForm);
        return "admin/fileList";
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
