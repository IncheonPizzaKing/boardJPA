package com.crowdsourcing.test.repository;

import com.crowdsourcing.test.domain.CommonCode;
import com.crowdsourcing.test.domain.CommonGroup;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommonGroupRepository extends JpaRepository<CommonGroup, String> {

    /**
     * JPA Repository 내장 메소드
     */
    Page<CommonGroup> findByGroupNameKorContaining(String groupNameKor, Pageable pageable);
}