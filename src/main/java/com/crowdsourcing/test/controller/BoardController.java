package com.crowdsourcing.test.controller;

import com.crowdsourcing.test.domain.Board;
import com.crowdsourcing.test.service.BoardService;
import com.crowdsourcing.test.service.FileService;
import com.crowdsourcing.test.util.MD5Generator;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
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
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final FileService fileService;

    @GetMapping("/board/new")
    public String createForm(Model model) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails userDetails = (UserDetails)principal;
        String username = ((UserDetails) principal).getUsername();
        BoardForm board = new BoardForm();
        board.setAuthor(username);
        model.addAttribute("boardForm", board);
        return "board/createBoardForm";
    }

    @PostMapping("/board/new")
    public String create(@Valid BoardForm boardForm, BindingResult result, MultipartFile multipartFile) throws Exception {

        if (result.hasErrors()) {
            return "board/createBoardForm";
        }
        Board board = new Board();
        board.setContentType(boardForm.getContentType());
        board.setTitle(boardForm.getTitle());
        board.setAuthor(boardForm.getAuthor());
        board.setContent(boardForm.getContent());
        board.setTime(LocalDateTime.now());

        if (!multipartFile.isEmpty()) {
            String originFilename = multipartFile.getOriginalFilename();
            String filename = new MD5Generator(originFilename).toString();
            String savePath = System.getProperty("user.dir") + "/files";
            if (!new File(savePath).exists()) {

                try {
                    new File(savePath).mkdir();
                } catch (Exception e) {
                    e.getStackTrace();
                }
            }
            String filePath = savePath + "/" + filename;
            multipartFile.transferTo(new File(filePath));

            com.crowdsourcing.test.domain.File fileDto = new com.crowdsourcing.test.domain.File();
            fileDto.setFileName(filename);
            fileDto.setFilePath(filePath);
            fileDto.setOriginFileName(originFilename);
            Long fileId = fileService.upload(fileDto);

            board.setFileId(fileId);
        }
        boardService.write(board);
        return "redirect:/board";
    }

    @GetMapping("/board")
    public String list(@ModelAttribute("boardSearch") BoardSearch boardSearch, Model model) {
        List<Board> boardList = boardService.findBoard(boardSearch);
        model.addAttribute("board", boardList);
        return "board/boardList";
    }

    @GetMapping("/download/{fileId}")
    public ResponseEntity<Resource> fileDownload(@PathVariable("fileId") Long fileId) throws IOException {
        com.crowdsourcing.test.domain.File fileDto = fileService.findOne(fileId);
        Path path = Paths.get(fileDto.getFilePath());
        Resource resource = new InputStreamResource(Files.newInputStream(path));
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; fileName=/" + fileDto.getOriginFileName() + "/")
                .body(resource);
    }

    @GetMapping("/board/{boardId}/update")
    public String updateBoardForm(@PathVariable("boardId") Long boardId, Model model) {
        Board board = (Board) boardService.findOne(boardId);
        BoardForm form = new BoardForm();
        form.setContentType(board.getContentType());
        form.setTitle(board.getTitle());
        form.setAuthor(board.getAuthor());
        form.setContent(board.getContent());

        model.addAttribute("form", form);
        return "board/updateBoardForm";
    }

    @PostMapping("/board/{boardId}/update")
    public String updateBoard(@PathVariable Long boardId, @ModelAttribute("form") BoardForm form) {
        boardService.update(boardId, form.getTitle(), form.getContent());
        return "redirect:/board";
    }

    @GetMapping("/board/{boardId}/delete")
    public String deleteBoard(@PathVariable Long boardId) {
        boardService.delete(boardId);
        return "redirect:/board";
    }
}

