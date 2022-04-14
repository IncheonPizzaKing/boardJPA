package com.crowdsourcing.test.repository;

import com.crowdsourcing.test.controller.form.BoardSearch;
import com.crowdsourcing.test.domain.Board;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
@Transactional
public class CustomBoardRepositoryImpl implements CustomBoardRepository {

    @Autowired
    EntityManager em;

    /**
     * 검색 조건에 만족하는 모든 게시글 조회
     */
    @Override
    public List<Board> findAll(BoardSearch boardSearch) {
        String jpql = "select b from Board b";
        String search = boardSearch.getSearch();
        String types = boardSearch.getTypes();
        if (StringUtils.hasText(search)) {
            jpql += " where b.title like concat('%',:search,'%')";
        }
        if (("none").equals(types)) {
            TypedQuery<Board> query = em.createQuery(jpql, Board.class);
            if (StringUtils.hasText(search)) {
                query = query.setParameter("search", search);
            }
            return query.getResultList();
        }
        if (StringUtils.hasText(types)) {
            if (StringUtils.hasText(search)) {
                jpql += " and b.contentType = :types";
            } else {
                jpql += " where b.contentType = :types";
            }
        }
        TypedQuery<Board> query = em.createQuery(jpql, Board.class)
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