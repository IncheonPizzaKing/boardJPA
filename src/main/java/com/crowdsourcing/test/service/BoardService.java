package com.crowdsourcing.test.service;

import com.crowdsourcing.test.domain.Board;
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

    //회원 가입
    @Transactional
    public Long write(Board board) {
        boardRepository.save(board);
        return board.getId();
    }

    //회원 전체 조회
    public List<Board> findBoard() {
        return boardRepository.findAll();
    }

    public Board findOne(Long boardId) {
        return boardRepository.findOne(boardId);
    }
}