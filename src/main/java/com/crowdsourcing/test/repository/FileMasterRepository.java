package com.crowdsourcing.test.repository;

import com.crowdsourcing.test.domain.File;
import com.crowdsourcing.test.domain.FileMaster;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class FileMasterRepository {
    private final EntityManager em;

    /**
     * 파일 마스터 저장
     */
    public void save(FileMaster fileMaster) {
        em.persist(fileMaster);
    }
}