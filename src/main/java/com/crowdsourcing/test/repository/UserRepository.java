package com.crowdsourcing.test.repository;

import com.crowdsourcing.test.domain.User;
import com.crowdsourcing.test.domain.UserId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User,UserId>, CustomUserRepository {

    @Query("SELECT u from User u WHERE u.username = :username")
    User findByUsername(@Param("username") String username);
}
