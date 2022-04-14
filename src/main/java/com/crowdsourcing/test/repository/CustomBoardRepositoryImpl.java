package com.crowdsourcing.test.repository;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.crowdsourcing.test.controller.form.BoardSearch;
import com.crowdsourcing.test.domain.Board;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
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
    public List<Board> findAll(BoardSearch boardSearch) {
        String search = boardSearch.getSearch();
        String types = boardSearch.getTypes();
        return query.selectFrom(board)
                .where(eqSearch(search),
                        eqType(types))
                .fetch();
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