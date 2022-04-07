package com.crowdsourcing.test.repository;

import com.crowdsourcing.test.controller.BoardSearch;
import com.crowdsourcing.test.domain.Board;
import com.crowdsourcing.test.domain.File;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

import static ch.qos.logback.core.joran.action.ActionConst.NULL;

@Repository
@RequiredArgsConstructor
public class BoardRepository {

    private final EntityManager em;

    public void save(Board board) {
        em.persist(board);
    }

    public void remove(Board board) {
        em.remove(board);
    }

    public Board findOne(Long id) {
        return em.find(Board.class, id);
    }

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