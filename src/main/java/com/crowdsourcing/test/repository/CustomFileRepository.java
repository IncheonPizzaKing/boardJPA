package com.crowdsourcing.test.repository;

import com.crowdsourcing.test.dto.SearchDto;
import com.crowdsourcing.test.domain.File;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomFileRepository {

    Page<File> findFile(SearchDto fileSearch, Pageable pageable);
}
