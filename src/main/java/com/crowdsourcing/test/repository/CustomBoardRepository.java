package com.crowdsourcing.test.repository;

import com.crowdsourcing.test.controller.form.BoardSearch;
import com.crowdsourcing.test.domain.Board;

import java.util.List;

public interface CustomBoardRepository {

    List<Board> findAll(BoardSearch boardSearch);
}
