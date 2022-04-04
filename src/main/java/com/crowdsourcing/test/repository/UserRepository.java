package com.crowdsourcing.test.repository;

import com.crowdsourcing.test.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

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
}
