package com.crowdsourcing.test.repository;

import com.crowdsourcing.test.domain.BoardFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardFileRepository extends JpaRepository<BoardFile, Long> {

}