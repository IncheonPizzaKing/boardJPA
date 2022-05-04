package com.crowdsourcing.test.controller;

import com.crowdsourcing.test.controller.form.BoardForm;
import com.crowdsourcing.test.controller.form.BoardSearch;
import com.crowdsourcing.test.domain.*;
import com.crowdsourcing.test.service.BoardService;
import com.crowdsourcing.test.service.CommonCodeService;
import com.crowdsourcing.test.service.FileMasterService;
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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final FileMasterService fileMasterService;
    private final CommonCodeService commonCodeService;

    /**
     * 게시글 작성 페이지 접속시
     */
    @GetMapping("/board/new")
    public String createForm(Model model) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails userDetails = (UserDetails) principal;
        String username = ((UserDetails) principal).getUsername();
        BoardForm board = new BoardForm();
        board.setAuthor(username);
        board.setCommonCodeList(commonCodeService.findByGroupCode("G001"));
        model.addAttribute("boardForm", board);
        return "board/createBoardForm :: #modalForm";
    }

    /**
     * 게시글 작성 버튼 클릭시
     */
    @PostMapping("/board/new")
    public String create(@ModelAttribute("boardForm") @Valid BoardForm boardForm, BindingResult result, List<MultipartFile> multipartFile) throws Exception {

        if (result.hasErrors()) {
            return "board/createBoardForm";
        }
        Board board = new Board();
        String[] commonCodeOne = boardForm.getCommonCodeId().split("_");
        CommonCode one = commonCodeService.findById(new CommonCodeId(commonCodeOne[0], commonCodeOne[1]));
        board.setCommonCode(one);
        board.setTitle(boardForm.getTitle());
        board.setContent(boardForm.getContent());
        board.setTime(LocalDateTime.now());
        if (!multipartFile.get(0).isEmpty()) {
            FileMaster fileMasterIn = fileMasterService.upload(multipartFile);
            board.setFileMaster(fileMasterIn);
        }
        boardService.write(board, boardForm.getAuthor());
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
    public ResponseEntity<Resource> fileDownload(@PathVariable("boardId") Long id, @PathVariable("file") int file) throws IOException {
        Board board = boardService.findOne(id);
        List<com.crowdsourcing.test.domain.File> fileList = board.getFileMaster().getFileList();
        com.crowdsourcing.test.domain.File fileDto = fileList.get(file);
        Path path = Paths.get(fileDto.getFilePath());
        Resource resource = new InputStreamResource(Files.newInputStream(path));
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;fileName=\\" + new String(fileDto.getOriginFileName().getBytes("UTF-8"), "ISO-8859-1"))
                .body(resource);
    }


    /**
     * 게시글 수정페이지 접속시
     */
    @GetMapping("/board/{boardId}/update")
    public String updateBoardForm(@PathVariable("boardId") Long boardId, Model model) {
        Board board = (Board) boardService.findOne(boardId);
        BoardForm form = new BoardForm();
        form.setCommonCode(board.getCommonCode());
        form.setTitle(board.getTitle());
        form.setAuthor(board.getAuthor().getUsername());
        form.setContent(board.getContent());

        model.addAttribute("form", form);
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

