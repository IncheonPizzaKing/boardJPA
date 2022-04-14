package com.crowdsourcing.test.repository;

import com.crowdsourcing.test.controller.form.BoardSearch;
import com.crowdsourcing.test.domain.User;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.crowdsourcing.test.domain.QUser.user;

@Repository
@Transactional
@RequiredArgsConstructor
public class CustomUserRepositoryImpl implements CustomUserRepository {

    private final JPAQueryFactory query;

    /**
     * 조건에 맞는 사용자 전체 조회
     */
    @Override
    public List<User> findUser(BoardSearch userSearch) {
        String jpql = "select u from User u";
        String search = userSearch.getSearch();
        String types = userSearch.getTypes();
        return query.selectFrom(user)
                .where(eqSearch(search),
                        eqType(types))
                .fetch();
    }

    private BooleanExpression eqSearch(String search) {
        if(!StringUtils.hasText(search)) {
            return null;
        }
        return user.username.contains(search);
    }

    private BooleanExpression eqType(String types) {
        if(types == null) {
            return null;
        }
        if(types.equals("none")) {
            return null;
        }
        return user.role.eq(types);
    }
}
