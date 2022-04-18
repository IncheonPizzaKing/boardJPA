package com.crowdsourcing.test.repository;

import com.crowdsourcing.test.controller.form.BoardSearch;
import com.crowdsourcing.test.domain.File;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomFileRepository {

    Page<File> findFile(BoardSearch fileSearch, Pageable pageable);
}
