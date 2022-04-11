package com.crowdsourcing.test.service;

import com.crowdsourcing.test.controller.BoardSearch;
import com.crowdsourcing.test.domain.Board;
import com.crowdsourcing.test.domain.File;
import com.crowdsourcing.test.domain.User;
import com.crowdsourcing.test.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final UserService userService;

    /**
     * 게시글 작성
     */
    @Transactional
    public void write(Board board, String author) {
        User user = userService.loadUserByUsername(author);
        board.addUser(user);
        boardRepository.save(board);
    }

    /**
     * 게시글 삭제
     */
    @Transactional
    public void delete(Long boardId) {
        Board board = boardRepository.findOne(boardId);
        boardRepository.remove(board);
    }

    /**
     * 게시글 수정
     */
    @Transactional
    public void update(Long boardId, String title, String content) {
        Board board = boardRepository.findOne(boardId);
        board.setTitle(title);
        board.setContent(content);
    }

    /**
     * 조건에 맞는 게시글 전체 조회
     */
    public List<Board> findBoard(BoardSearch boardSearch) {
        return boardRepository.findAll(boardSearch);
    }

    /**
     * 게시글 한 개 조회(id)
     */
    public Board findOne(Long boardId) {
        return boardRepository.findOne(boardId);
    }

}