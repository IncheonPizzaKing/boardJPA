package com.crowdsourcing.test.repository;

import com.crowdsourcing.test.domain.CommonCode;
import com.crowdsourcing.test.domain.CommonCodeId;
import com.crowdsourcing.test.domain.CommonGroup;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommonCodeRepository extends JpaRepository<CommonCode, CommonCodeId> {

    /**
     * JPA Repository 내장 메소드
     * ex) findByGroupCodeEquals(String groupCode) -> SELECT * FROM COMMON_CODE WHERE GROUP_CODE == GROUP_CODE;
     */
    List<CommonCode> findByGroupCodeEquals(String groupCode);
    Page<CommonCode> findByCodeNameKorContaining(String commonCodeNameKor, Pageable pageable);
    Page<CommonCode> findByCodeNameKorContainingAndCommonGroupEquals(String commonCodeNameKor, CommonGroup commonGroup, Pageable pageable);
}