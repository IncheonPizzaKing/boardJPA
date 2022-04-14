package com.crowdsourcing.test.repository;

import com.crowdsourcing.test.controller.form.BoardSearch;
import com.crowdsourcing.test.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
@Transactional
public class CustomUserRepositoryImpl implements CustomUserRepository {

    @Autowired
    EntityManager em;

    /**
     * 조건에 맞는 사용자 전체 조회
     */
    @Override
    public List<User> findUser(BoardSearch userSearch) {
        String jpql = "select u from User u";
        String search = userSearch.getSearch();
        String types = userSearch.getTypes();
        if (StringUtils.hasText(search)) {
            jpql += " where u.username like concat('%',:search,'%')";
        }
        if (("none").equals(types)) {
            TypedQuery<User> query = em.createQuery(jpql, User.class);
            if (StringUtils.hasText(search)) {
                query = query.setParameter("search", search);
            }
            return query.getResultList();
        }
        if (StringUtils.hasText(types)) {
            if (StringUtils.hasText(search)) {
                jpql += " and u.role = :types";
            } else {
                jpql += " where u.role = :types";
            }
        }
        TypedQuery<User> query = em.createQuery(jpql, User.class)
                .setMaxResults(1000); //최대 1000건
        if (StringUtils.hasText(types)) {
            query = query.setParameter("types", types);
        }
        if (StringUtils.hasText(search)) {
            query = query.setParameter("search", search);
        }
        return query.getResultList();
    }
}
