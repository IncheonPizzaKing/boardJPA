package com.crowdsourcing.test.repository;

import com.crowdsourcing.test.controller.BoardSearch;
import com.crowdsourcing.test.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserRepository {
    private final EntityManager em;

    public void save(User user) {
        em.persist(user);
    }

    public void remove(User user) {
        em.remove(user);
    }

    public User findByUsername(String username) {
        User user = em.createQuery(
                        "SELECT u from User u WHERE u.username = :username", User.class).
                setParameter("username", username).getSingleResult();
        return user;
    }

    public User findOne(Long id) {
        return em.find(User.class, id);
    }

    public List<User> findUser(BoardSearch userSearch) {
        String jpql = "select u from User u";
        String search = userSearch.getSearch();
        String types = userSearch.getTypes();
        if (StringUtils.hasText(search)) {
            if (StringUtils.hasText(types)) {
                jpql += " and u.username like concat('%',:search,'%')";
            } else {
                jpql += " where u.username like concat('%',:search,'%')";
            }
        }
        if (("none").equals(types)) {
            TypedQuery<User> query = em.createQuery(jpql, User.class);
            return query.getResultList();
        }
        if (StringUtils.hasText(types)) {
            jpql += " where u.role = :types";
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
