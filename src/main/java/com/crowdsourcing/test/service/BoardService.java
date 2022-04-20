package com.crowdsourcing.test.service;

import com.crowdsourcing.test.controller.form.BoardSearch;
import com.crowdsourcing.test.domain.Board;
import com.crowdsourcing.test.domain.User;
import com.crowdsourcing.test.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final UserService userService;
    private final FileMasterService fileMasterService;

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
        Board board = boardRepository.findById(boardId).orElseThrow(IllegalArgumentException::new);
        if(board.getFileMaster() != null) {
            fileMasterService.deleteBoard(board.getFileMaster());
        }
        boardRepository.delete(board);
    }

    /**
     * 게시글 수정
     */
    @Transactional
    public void update(Long boardId, String title, String content) {
        Board board = boardRepository.findById(boardId).orElseThrow(IllegalArgumentException::new);
        board.setTitle(title);
        board.setContent(content);
    }

    /**
     * 조건에 맞는 게시글 전체 조회
     */
    public Page<Board> findBoard(BoardSearch boardSearch, Pageable pageable) {
        return boardRepository.findAll(boardSearch, pageable);
    }

    /**
     * 게시글 한 개 조회(id)
     */
    public Board findOne(Long boardId) {
        return boardRepository.findById(boardId).orElseThrow(IllegalArgumentException::new);
    }

}