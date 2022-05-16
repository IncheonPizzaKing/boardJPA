package com.crowdsourcing.test.repository;

import com.crowdsourcing.test.domain.BoardUser;
import com.crowdsourcing.test.domain.CommonCode;
import com.crowdsourcing.test.domain.UserId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<BoardUser,UserId> {

    /**
     * JPA Repository 내장 메소드
     * ex) findByUsernameContaining == Select * from user where username like %search%
     */
    BoardUser findByUsernameEquals(String username);
    Page<BoardUser> findByUsernameContaining(String search, Pageable pageable);
    Page<BoardUser> findByUsernameContainingAndCommonCodeEquals(String search, CommonCode commonCode, Pageable pageable);
}
