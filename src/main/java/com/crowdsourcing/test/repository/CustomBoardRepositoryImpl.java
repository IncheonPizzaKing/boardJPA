package com.crowdsourcing.test.repository;

import com.crowdsourcing.test.controller.form.BoardSearch;
import com.crowdsourcing.test.domain.Board;
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
@RequiredArgsConstructor /** final이나 @NonNull인 필드 값만 파라미터로 받는 생성자를 추가 */
public class CustomBoardRepositoryImpl implements CustomBoardRepository {

    private final JPAQueryFactory query;

    /**
     * 검색 조건에 만족하는 모든 게시글 조회
     * querydsl 사용 -> 쿼리를 자바 코드로 작성할 수 있게 도와주는 기술
     */
    @Override
    public Page<Board> findAll(BoardSearch boardSearch, Pageable pageable) {
        String search = boardSearch.getSearch();
        String[] types = boardSearch.getTypes().split("_");
        String groupCode = types[0];
        String code = "null";
        if(types.length >= 2) {
            code = types[1];
        }
        List<Board> list = query.selectFrom(board)
                .where(eqSearch(search),
                        eqGroupCode(groupCode),
                        eqCode(code))
                .orderBy(board.id.desc())
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

    private BooleanExpression eqGroupCode(String groupCode) {
        if(groupCode.equals("null")) {
            return null;
        }
        if(groupCode.equals("none")) {
            return null;
        }
        return board.commonCode.commonGroup.groupCode.eq(groupCode);
    }
    private BooleanExpression eqCode(String code) {
        if(code.equals("null")) {
            return null;
        }
        return board.commonCode.code.eq(code);
    }
}