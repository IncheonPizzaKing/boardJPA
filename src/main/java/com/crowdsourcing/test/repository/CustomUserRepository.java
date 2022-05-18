package com.crowdsourcing.test.repository;

import com.crowdsourcing.test.domain.BoardUser;
import com.crowdsourcing.test.domain.CommonCode;
import com.crowdsourcing.test.domain.UserId;
import com.crowdsourcing.test.dto.SearchDto;
import com.crowdsourcing.test.dto.user.UserListDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomUserRepository {

    Page<UserListDto> findUserList(SearchDto searchDto, Pageable pageable);
}
