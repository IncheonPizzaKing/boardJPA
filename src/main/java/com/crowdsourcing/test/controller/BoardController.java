package com.crowdsourcing.test.controller;

import com.crowdsourcing.test.controller.form.BoardForm;
import com.crowdsourcing.test.controller.form.BoardSearch;
import com.crowdsourcing.test.domain.*;
import com.crowdsourcing.test.service.BoardService;
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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final CommonCodeService commonCodeService;
    private final FileService fileService;

    /**
     * 게시글 작성 페이지 접속시
     */
    @GetMapping("/board/new")
    public String createForm(Model model) {
        model.addAttribute("boardForm", new BoardForm());
        model.addAttribute("commonCodeList", commonCodeService.findByGroupCode("G001"));
        return "board/createBoardForm :: #modalForm";
    }

    /**
     * 게시글 작성 버튼 클릭시
     */
    @PostMapping("/board/new")
    public String write(@ModelAttribute("boardForm") @Valid BoardForm boardForm, BindingResult result, List<MultipartFile> multipartFile) throws Exception {
        if (result.hasErrors()) {
            return "board/createBoardForm";
        }
        boardService.write(boardForm, multipartFile);
        return "redirect:/board";
    }

    /**
     * 게시글 조회시
     */
    @GetMapping("/board")
    public String list(Model model) {
        model.addAttribute("commonCodeList", commonCodeService.findByGroupCode("G001"));
        model.addAttribute("sizeList", commonCodeService.findByGroupCode("G004"));
        return "board/boardList";
    }

    /**
     * 게시글 검색시
     */
    @PostMapping("/board")
    public String paging(@RequestParam Map<String, Object> param, Model model, @PageableDefault(size = 10, sort = "board_id", direction = Sort.Direction.DESC) Pageable pageable) {
        BoardSearch boardSearch = new BoardSearch();
        if (param.get("types") != null) {
            boardSearch.setTypes(param.get("types").toString());
        }
        if (param.get("search") != null) {
            boardSearch.setSearch(param.get("search").toString());
        }
        Page<Board> boardList = boardService.findBoard(boardSearch, pageable);
        int startPage = 1, endPage;
        int totalPages = boardList.getTotalPages();
        if (totalPages == 0) {
            endPage = 1;
        } else {
            endPage = totalPages;
        }
        model.addAttribute("board", boardList);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "board/boardList :: #viewList";
    }

    /**
     * 첨부파일 다운로드 버튼 클릭시
     */
    @GetMapping("/download/{boardId}/{file}")
    public ResponseEntity<Resource> fileDownload(@PathVariable("boardId") Long id, @PathVariable("file") Long file) throws Exception {
        return fileService.downloadFile(file);
    }


    /**
     * 게시글 수정페이지 접속시
     */
    @GetMapping("/board/{boardId}/update")
    public String updateBoardForm(@PathVariable("boardId") Long boardId, Model model) {
        model.addAttribute("form", boardService.updateBoardForm(boardId));
        return "board/updateBoardForm :: #modalForm";
    }


    /**
     * 게시글 수정 버튼 클릭시
     */
    @PostMapping("/board/{boardId}/update")
    public String updateBoard(@ModelAttribute("form") @Valid BoardForm form, BindingResult result, @PathVariable Long boardId) {
        if (result.hasErrors()) {
            return "board/updateBoardForm";
        }
        boardService.update(boardId, form.getTitle(), form.getContent());
        return "redirect:/board";
    }


    /**
     * 게시글 삭제 버튼 클릭시
     */
    @GetMapping("/board/{boardId}/delete")
    public String deleteBoard(@PathVariable Long boardId) {
        boardService.delete(boardId);
        return "redirect:/board";
    }
}

