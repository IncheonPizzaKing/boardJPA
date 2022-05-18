package com.crowdsourcing.test.repository;

import com.crowdsourcing.test.dto.SearchDto;
import com.crowdsourcing.test.domain.Board;
import com.crowdsourcing.test.dto.board.BoardListDto;
import com.querydsl.core.Tuple;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomBoardRepository {

    Page<BoardListDto> findDslAll(SearchDto searchDto, Pageable pageable);
}
