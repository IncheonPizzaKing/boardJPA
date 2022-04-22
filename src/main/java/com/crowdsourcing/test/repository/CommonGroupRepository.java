package com.crowdsourcing.test.repository;

import com.crowdsourcing.test.domain.CommonGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommonGroupRepository extends JpaRepository<CommonGroup, String> {
}