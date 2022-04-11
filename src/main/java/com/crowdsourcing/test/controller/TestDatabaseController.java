package com.crowdsourcing.test.controller;

import com.crowdsourcing.test.domain.Board;
import com.crowdsourcing.test.domain.User;
import com.crowdsourcing.test.service.BoardService;
import com.crowdsourcing.test.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class TestDatabaseController {

    private final UserService userService;
    private final BoardService boardService;

    /**
     * 테스트 데이터 주입
     */
    public void create() {
        createTestDB("user", "user", "USER");
        createTestDB("admin", "admin", "ADMIN,USER");

        createTestDB2("free", "test1", "user", "test1");
        createTestDB2("sports", "test2", "user", "test2");
        createTestDB2("movie", "test3", "admin", "test3");
        createTestDB2("music", "test4", "admin", "test4");
    }

    /**
     * 테스트 사용자 데이터 메소드
     */
    public void createTestDB(String name, String password, String role) {
        User user = new User();
        user.setUsername(name);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(password));
        user.setRole(role);

        userService.save(user);
    }

    /**
     * 테스트 게시글 데이터 메소드
     */
    public void createTestDB2(String type, String title, String author, String content) {
        Board board = new Board();
        board.setContentType(type);
        board.setTitle(title);
        board.setContent(content);
        board.setTime(LocalDateTime.now());
        List<MultipartFile> test = new ArrayList<>();

        boardService.write(board, author);
    }
}
