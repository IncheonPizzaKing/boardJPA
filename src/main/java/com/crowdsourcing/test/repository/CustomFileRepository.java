package com.crowdsourcing.test.repository;

import com.crowdsourcing.test.controller.form.BoardSearch;
import com.crowdsourcing.test.domain.File;

import java.util.List;

public interface CustomFileRepository {

    List<File> findFile(BoardSearch fileSearch);
}
