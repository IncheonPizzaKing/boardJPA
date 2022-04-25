package com.crowdsourcing.test.repository;

import com.crowdsourcing.test.domain.CommonCode;
import com.crowdsourcing.test.domain.User;
import com.crowdsourcing.test.domain.UserId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User,UserId> {

    @Query("SELECT u from User u WHERE u.username = :username")
    User findByUsername(@Param("username") String username);

    /**
     * JPA Repository 내장 메소드
     * ex) findByUsernameContaining == Select * from user where username like %search%
     */
    Page<User> findByUsernameContaining(String search, Pageable pageable);
    Page<User> findByUsernameContainingAndCommonCodeEquals(String search, CommonCode commonCode, Pageable pageable);
}
