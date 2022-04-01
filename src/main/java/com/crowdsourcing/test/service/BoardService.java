package com.crowdsourcing.test.service;

import com.crowdsourcing.test.controller.BoardSearch;
import com.crowdsourcing.test.domain.Board;
import com.crowdsourcing.test.domain.File;
import com.crowdsourcing.test.repository.BoardRepository;
import com.crowdsourcing.test.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final FileService fileService;

    //게시글 작성
    @Transactional
    public Long write(Board board) {
        boardRepository.save(board);
        return board.getId();
    }

    @Transactional
    public void delete(Long boardId) {
        Board board = boardRepository.findOne(boardId);
        Long fileId = board.getFileId();
        if (fileId != null) {
            fileService.delete(fileId);
        }
        boardRepository.remove(board);
    }

    //게시글 수정
    @Transactional
    public void update(Long boardId, String title, String content) {
        Board board = boardRepository.findOne(boardId);
        board.setTitle(title);
        board.setContent(content);
    }

    //회원 전체 조회
    public List<Board> findBoard(BoardSearch boardSearch) {
        return boardRepository.findAll(boardSearch);
    }

    public Board findOne(Long boardId) {
        return boardRepository.findOne(boardId);
    }
}