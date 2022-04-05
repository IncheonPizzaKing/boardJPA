package com.crowdsourcing.test.controller;

import com.crowdsourcing.test.domain.Board;
import com.crowdsourcing.test.domain.User;
import com.crowdsourcing.test.service.BoardService;
import com.crowdsourcing.test.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
public class TestDatabaseController {

    private final UserService userService;
    private final BoardService boardService;

    public void create() {
        createTestDB("aaa111@naver.com", "11", "USER");
        createTestDB("bbb222@naver.com", "11", "USER,ADMIN");
        createTestDB("ccc333@naver.com", "11", "USER");

        createTestDB2("free", "test1", "aaa111@naver.com", "test1");
        createTestDB2("sports", "test2", "bbb222@naver.com", "test2");
        createTestDB2("movie", "test3", "ccc333@naver.com", "test3");
        createTestDB2("music", "test4", "aaa111@naver.com", "test4");
    }

    public void createTestDB(String name, String password, String role) {
        User user = new User();
        user.setUsername(name);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(password));
        user.setRole(role);

        userService.save(user);
    }

    public void createTestDB2(String type, String title, String author, String content) {
        Board board = new Board();
        board.setContentType(type);
        board.setTitle(title);
        board.setAuthor(author);
        board.setContent(content);
        board.setTime(LocalDateTime.now());

        boardService.write(board);
    }
}
