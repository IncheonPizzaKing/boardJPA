package com.crowdsourcing.test.repository;

import com.crowdsourcing.test.domain.Board;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

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

    public List<Board> findAll() {
        return em.createQuery("select m from Board m", Board.class)
                .getResultList();
    }

    public List<Board> findByTitle(String name) {
        return em.createQuery("select m from Board m where m.title = %:title%", Board.class)
                .setParameter("name", name)
                .getResultList();
    }
}