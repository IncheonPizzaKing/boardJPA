package com.crowdsourcing.test.repository;

import com.crowdsourcing.test.controller.form.BoardSearch;
import com.crowdsourcing.test.domain.Board;
import com.crowdsourcing.test.domain.File;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.crowdsourcing.test.domain.QBoard.board;

@Repository
@Transactional
@RequiredArgsConstructor
public class CustomBoardRepositoryImpl implements CustomBoardRepository {

    private final JPAQueryFactory query;

    /**
     * 검색 조건에 만족하는 모든 게시글 조회
     */
    @Override
    public Page<Board> findAll(BoardSearch boardSearch, Pageable pageable) {
        String search = boardSearch.getSearch();
        String types = boardSearch.getTypes();
        List<Board> list = query.selectFrom(board)
                .where(eqSearch(search),
                        eqType(types))
                .fetch();
        int start = (int)pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), list.size());
        Page<Board> page = new PageImpl<Board>(list.subList(start, end), pageable, list.size());
        return page;
    }

    private BooleanExpression eqSearch(String search) {
        if(!StringUtils.hasText(search)) {
            return null;
        }
        return board.title.contains(search);
    }

    private BooleanExpression eqType(String types) {
        if(types == null) {
            return null;
        }
        if(types.equals("none")) {
            return null;
        }
        return board.contentType.eq(types);
    }
}