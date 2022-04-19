package com.crowdsourcing.test.repository;

import com.crowdsourcing.test.controller.form.BoardSearch;
import com.crowdsourcing.test.domain.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomBoardRepository {

    Page<Board> findAll(BoardSearch boardSearch, Pageable pageable);
}
