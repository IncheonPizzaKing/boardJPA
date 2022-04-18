package com.crowdsourcing.test.repository;

import com.crowdsourcing.test.controller.form.BoardSearch;
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

import java.awt.*;
import java.util.List;

import static com.crowdsourcing.test.domain.QFile.file;

@Repository
@Transactional
@RequiredArgsConstructor
public class CustomFileRepositoryImpl implements CustomFileRepository {

    private final JPAQueryFactory query;

    /**
     * 조건에 맞는 파일 전체 조회
     */
    @Override
    public Page<File> findFile(BoardSearch fileSearch, Pageable pageable) {
        String search = fileSearch.getSearch();
        List<File> list = query.selectFrom(file)
                .where(eqSearch(search))
                .fetch();
        int start = (int)pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), list.size());
        Page<File> page = new PageImpl<File>(list.subList(start, end), pageable, list.size());
        return page;
    }

    private BooleanExpression eqSearch(String search) {
        if(!StringUtils.hasText(search)) {
            return null;
        }
        return file.originFileName.contains(search);
    }
}
