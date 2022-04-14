package com.crowdsourcing.test.repository;

import com.crowdsourcing.test.domain.FileMaster;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileMasterRepository extends JpaRepository<FileMaster, Long> {
}