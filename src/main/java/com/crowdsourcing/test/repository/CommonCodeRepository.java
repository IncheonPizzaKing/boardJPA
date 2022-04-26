package com.crowdsourcing.test.repository;

import com.crowdsourcing.test.domain.CommonCode;
import com.crowdsourcing.test.domain.CommonCodeId;
import com.crowdsourcing.test.domain.CommonGroup;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommonCodeRepository extends JpaRepository<CommonCode, CommonCodeId> {

    /**
     * JPA Repository 내장 메소드
     */
    Page<CommonCode> findByCodeNameKorContaining(String commonCodeNameKor, Pageable pageable);
    Page<CommonCode> findByCodeNameKorContainingAndCommonGroupEquals(String commonCodeNameKor, CommonGroup commonGroup, Pageable pageable);
}