package com.crowdsourcing.test.repository;

import com.crowdsourcing.test.domain.File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<File, Long>, CustomFileRepository {
}
