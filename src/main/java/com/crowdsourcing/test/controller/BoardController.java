package com.crowdsourcing.test.controller;

import com.crowdsourcing.test.domain.Board;
import com.crowdsourcing.test.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/board/new")
    public String createForm(Model model) {
        model.addAttribute("boardForm", new BoardForm());
        return "board/createBoardForm";
    }

    @PostMapping("/board/new")
    public String create(@Valid BoardForm boardForm, BindingResult result) {

        if (result.hasErrors()) {
            return "board/createBoardForm";
        }
        Board board = new Board();
        board.setContentType(boardForm.getContentType());
        board.setTitle(boardForm.getTitle());
        board.setAuthor(boardForm.getAuthor());
        board.setContent(boardForm.getContent());
        board.setTime(LocalDateTime.now());

        boardService.write(board);
        return "redirect:/";
    }

    @GetMapping("/board")
    public String list(Model model) {
        List<Board> board = boardService.findBoard();
        model.addAttribute("board", board);
        return "board/boardList";
    }
}
